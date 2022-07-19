package com.example.vocabularytrainer.presentation.detail_group

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.presentation.components.LoadAnimation
import com.example.vocabularytrainer.presentation.components.LoadingAnimationType
import com.example.vocabularytrainer.presentation.detail_group.components.WordItem
import com.example.vocabularytrainer.presentation.home.HomeEvent
import com.example.vocabularytrainer.presentation.home.LoadingType
import com.vmakd1916gmail.com.core.util.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailGroupScreen(
    groupId: String,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: DetailGroupViewModel = hiltViewModel(),
    changeFabPosition: () -> Unit
) {
    val state = viewModel.state
    DetailGroupViewModel.groupId = groupId
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        DetailGroupEvent.GetAllWordsByGroup.loadingType = LoadingType.FullScreenLoading()
    }


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

        else -> {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(end = 40.dp)
                .background(Color.White)) {

                items(state.words, key = { it.id }) { item ->
                    WordItem(
                        modifier = Modifier
                            .animateItemPlacement()
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        word = item.word,
                        translate = item.translate,
                        transcription = item.transcription,
                        visible = true,
                        onDelete = {

                        },
                        isSync = item.isSync,
                        isExpanded = item.isExpanded,
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row {
                                    Text(text = "TEST")
                                }
                            }
                        },
                        onToggleClick = {
                            viewModel.onDetailGroupEvent(DetailGroupEvent.OnToggleClick(item.id))
                        }
                    )

                }

            }
        }
    }


}