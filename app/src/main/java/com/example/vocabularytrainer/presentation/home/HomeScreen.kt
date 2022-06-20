package com.example.vocabularytrainer.presentation.home

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.components.LoadAnimation
import com.example.vocabularytrainer.presentation.components.LoadingAnimationType
import com.example.vocabularytrainer.presentation.home.components.GroupItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.flow.collectIndexed

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val state = viewModel.state
    LaunchedEffect(key1 = context) {
        viewModel.uiEvent?.collectIndexed { index, event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    BackHandler {

    }

    var visible by remember { mutableStateOf(false) }
    val isRefreshing by viewModel.isRefreshing.collectAsState()

//    val groups: List<Group> = state.group ?: listOf()





    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            viewModel.refresh()
        },
    ) {

        when(state.screenState){
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
            modifier = Modifier.fillMaxSize()
        ) {

            items(state.group.distinct(), key = { it.id }) { item ->
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
                            onDelete = {}
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
                            onDelete = {}
                        )
                    }
                }
            }
        }
    }
}

