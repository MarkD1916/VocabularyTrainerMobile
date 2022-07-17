package com.example.vocabularytrainer.presentation.train

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.presentation.home.HomeViewModel
import com.vmakd1916gmail.com.core.util.UiEvent

@Composable
fun TrainScreen(
    groupId: String,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrainViewModel = hiltViewModel(),
) {

}