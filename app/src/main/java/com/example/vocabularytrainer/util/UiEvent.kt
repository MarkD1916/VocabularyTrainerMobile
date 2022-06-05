package com.vmakd1916gmail.com.core.util

sealed class UiEvent {
    data class Navigate(val route:String): UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()
}