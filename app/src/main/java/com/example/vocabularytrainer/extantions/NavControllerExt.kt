package com.example.vocabularytrainer.extantions

import androidx.navigation.NavController
import com.example.vocabularytrainer.navigation.Route
import com.vmakd1916gmail.com.core.util.UiEvent

fun NavController.navigateEvent(event: UiEvent.Navigate){
    this.navigate(event.route){
        if(event.route == Route.REGISTER || event.route == Route.LOGIN) {
            popUpTo(Route.WELCOME) {
                inclusive = false
            }
        }
//        if(event.route == Route.HOME) {
//            popUpTo(Route.WELCOME) {
//                inclusive = true
//            }
//        }

        if(event.route == Route.WELCOME) {
            popUpTo(Route.HOME) {
                inclusive = true
            }
        }

    }

}