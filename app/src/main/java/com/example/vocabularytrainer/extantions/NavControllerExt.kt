package com.example.vocabularytrainer.extantions

import androidx.navigation.NavController
import com.vmakd1916gmail.com.core.util.UiEvent

fun NavController.navigateEvent(event: UiEvent.Navigate){
    this.navigate(event.route)
}