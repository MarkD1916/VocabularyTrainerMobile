package com.example.vocabularytrainer.presentation.detail_group

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.home.LoadingType
import com.example.vocabularytrainer.presentation.home.Resource
import com.example.vocabularytrainer.presentation.home.components.GroupItem
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
        changeFabPosition()
        DetailGroupEvent.GetAllWordsByGroup.loadingType = LoadingType.FullScreenLoading()
        viewModel.onDetailGroupEvent(DetailGroupEvent.GetAllWordsByGroup)
    }



    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 30.dp)
        .background(Color.Green)) {

            items(state.words, key = { it.word }) { item ->
                GroupItem(
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    groupName = item.word,
                    visible = true,
                    onDelete = {

                    },
                    isSync = item.isSync,
                    isExpanded = true,
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

                    }
                )

    }

    }

}