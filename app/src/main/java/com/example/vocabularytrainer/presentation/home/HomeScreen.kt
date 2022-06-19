package com.example.vocabularytrainer.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.presentation.components.LoadAnimation
import com.example.vocabularytrainer.presentation.components.LoadingAnimationType
import com.example.vocabularytrainer.presentation.home.components.GroupItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.flow.collectIndexed

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

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            viewModel.refresh()
        },
    ) {

        when (state.group) {
            is Resource.NoAction -> {

            }
            is Resource.Loading -> {

                when (state.group) {
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

                    is LoadingType.ElementLoading -> {
                        visible = false
                        Column(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier
                            ) {
                                items(state.group.data!!) { item ->
                                    GroupItem(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 10.dp),
                                        groupName = item.name,
                                        visible = visible
                                    )
                                }
                            }
                        }
                    }

                    is LoadingType.LoadingFromDB -> {
                        visible = true
                        Column(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier
                            ) {
                                items(state.group.data!!) { item ->
                                    GroupItem(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 10.dp),
                                        groupName = item.name,
                                        visible = visible
                                    )
                                }
                            }
                        }
                    }
                }

            }
            is Resource.Success -> {
                visible = true
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(state.group.data!!) { item ->
                        GroupItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            groupName = item.name,
                            visible = visible
                        )
                    }
                }
            }
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                viewModel.onHomeEvent(
                                    HomeEvent
                                        .GetAllGroup
                                )
                            }) {
                            Text(text = "Try again")
                        }
                        Text(text = state.group.message!!)
                    }

                }
            }
        }

    }
}

//@Composable
//fun successResult(){
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        items(state.group.data!!) { item ->
//            Text(text = item.name)
//        }
//    }
//}

//    when (state.loginResponseResult) {
//        is AuthResponseResult.Loading -> {
//            Box(modifier = Modifier.fillMaxSize()) {
//                LoadAnimation(
//                    modifier =
//                    Modifier
//                        .align(Alignment.Center)
//                        .size(300.dp),
//                    radius = 200f,
//                    stroke = 27f,
//                    animationType = LoadingAnimationType.Screen
//                )
//            }
//        }
//        is AuthResponseResult.Success -> {
//        }
//        else -> {
//        }
//    }


