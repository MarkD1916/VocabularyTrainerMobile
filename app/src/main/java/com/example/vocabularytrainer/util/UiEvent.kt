package com.vmakd1916gmail.com.core.util

import com.example.vocabularytrainer.presentation.home.HomeEvent

sealed class UiEvent {
    data class Navigate(val route:String): UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()
    data class OpenAddGroupDialog(val open: Boolean): UiEvent()
}