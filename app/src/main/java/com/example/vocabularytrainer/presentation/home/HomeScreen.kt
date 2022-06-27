package com.example.vocabularytrainer.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.components.LoadAnimation
import com.example.vocabularytrainer.presentation.components.LoadingAnimationType
import com.example.vocabularytrainer.presentation.home.components.FabLoadingAnimation
import com.example.vocabularytrainer.presentation.home.components.GroupItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HomeViewModel,
    showBars: () -> Unit
) {
    showBars()
    val context = LocalContext.current


    val state = viewModel.state



    LaunchedEffect(key1 = context) {

        viewModel.uiEvent?.collectIndexed { index, event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.OpenAddGroupDialog -> {
                }
                else -> Unit
            }
        }
    }
    BackHandler {

    }

    var visible by remember { mutableStateOf(false) }
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    if (viewModel.isOpen) {

        AlertDialog(
            onDismissRequest = {

                viewModel.isOpen = false
            },
            title = {
                Text("Create new group")
            },
            text = {

                TextField(
                    modifier = Modifier.heightIn(max=150.dp),
                    value = state.newGroupName,
                    onValueChange = {
                        viewModel.onHomeEvent(HomeEvent.OnNewGroupNameEnter(it))
                    },
                    placeholder = { Text(text = "Enter group name") },
                    singleLine = true
                )


            },
            confirmButton = {
                Button(
                    onClick = {

                        viewModel.isOpen = false
                        viewModel.onHomeEvent(
                            HomeEvent.PostNewGroup(
                                Group(
                                    id = UUID.randomUUID().toString(),
                                    name = state.newGroupName,
                                    isSync = false,
                                    state = Resource.NoAction(null),
                                    date = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                )
                            )
                        )
                    }) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.isOpen = false
                    }) {
                    Text("Cancel")
                }
            }
        )
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            viewModel.refresh()
        },
    ) {

        when (state.screenState) {
            is LoadingType.FullScreenLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    LoadAnimation(
                        modifier =
                        Modifier
                            .align(Alignment.Center)
                            .size(300.dp),
                        radius = 200f,
                        stroke = 27f,
                        animationType = LoadingAnimationType.Screen
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp)
        ) {

            items(state.group.distinct(), key = { it.name }) { item ->
                when (item.state) {
                    is Resource.NoAction -> {
                        visible = true
                        GroupItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            groupName = item.name,
                            visible = visible,
                            onDelete = {
                                viewModel.onHomeEvent(HomeEvent.DeleteGroup(item.id))
                            },
                            isSync = item.isSync,
                            isExpanded = item.isExpanded,
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row() {

                                    }
                                }
                            },
                            onToggleClick = {
                                viewModel.onHomeEvent(HomeEvent.OnToggleGroupClick(item.id))
                            }
                        )
                    }

                    is Resource.Success -> {
                        visible = true
                        GroupItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            groupName = item.name,
                            visible = visible,
                            onDelete = {
                                viewModel.onHomeEvent(HomeEvent.DeleteGroup(item.id))
                            },
                            isSync = item.isSync,
                            isExpanded = item.isExpanded,
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 15.dp)
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(35.dp)
                                                .clickable {
                                                    viewModel.onHomeEvent(
                                                        HomeEvent.OnDetailGroupClick(
                                                            item.id
                                                        )
                                                    )
                                                },
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "choose",
                                            tint = Color.Black
                                        )
                                        Image(
                                            painterResource(R.drawable.train_icon_black),
                                            contentDescription = "",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .size(35.dp)
                                                .align(Alignment.CenterVertically)
                                        )
                                        Icon(
                                            modifier = Modifier
                                                .size(35.dp),
                                            imageVector = Icons.Default.Email,
                                            contentDescription = "choose",
                                            tint = Color.Black
                                        )
                                        Icon(
                                            modifier = Modifier
                                                .size(35.dp),
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "choose",
                                            tint = Color.Black
                                        )

                                        Image(
                                            painterResource(R.drawable.local_db_icon),
                                            contentDescription = "",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .size(35.dp)
                                                .align(Alignment.CenterVertically)
                                        )
                                    }
                                }
                            },
                            onToggleClick = {
                                viewModel.onHomeEvent(HomeEvent.OnToggleGroupClick(item.id))
                            }
                        )
                    }

                    is LoadingType.ElementLoading -> {
                        visible = false
                        GroupItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            groupName = item.name,
                            visible = visible,
                            onDelete = {},
                            isSync = item.isSync,
                            isExpanded = item.isExpanded,
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(text = "test text")
                                }
                            },
                            onToggleClick = {
                                viewModel.onHomeEvent(HomeEvent.OnToggleGroupClick(item.id))
                            }
                        )
                    }

                    is LoadingType.LoadingFromDB -> {
                        visible = true
                        GroupItem(
                            modifier = Modifier
                                .animateItemPlacement()
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            groupName = item.name,
                            visible = visible,
                            onDelete = {},
                            isSync = item.isSync,
                            isExpanded = item.isExpanded,
                            content = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(text = "test text")
                                }
                            },
                            onToggleClick = {
                                viewModel.onHomeEvent(HomeEvent.OnToggleGroupClick(item.id))
                            }
                        )
                    }
                }
            }
//            item {
//                FabLoadingAnimation(percentage = 0.9f)
//            }
        }
    }
}

