package com.example.vocabularytrainer.presentation.detail_group

import androidx.activity.compose.BackHandler
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.vocabularytrainer.presentation.home.HomeViewModel
import com.vmakd1916gmail.com.core.util.UiEvent

@Composable
fun DetailGroupScreen(
    groupId: String,
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    Text(text = groupId)
}