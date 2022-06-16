package com.example.vocabularytrainer.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult
import com.example.vocabularytrainer.presentation.components.LoadAnimation
import com.example.vocabularytrainer.presentation.components.LoadingAnimationType
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.flow.collectIndexed
import javax.inject.Qualifier

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
    Column {
        Button(onClick = { viewModel.onHomeEvent(HomeEvent.Action1) }) {
            if (state.actionState_1 is Resource.Loading) {
                Text(text = "Loading")
            }
            if (state.actionState_1 is Resource.NoAction) {
                Text(text = state.actionResult_1)
            }
            else {
                Text(text = state.getActionResult1())
            }

        }

        Button(onClick = { viewModel.onHomeEvent(HomeEvent.Action2) }) {
            if (state.actionState_2 is Resource.Loading) {
                Text(text = "Loading")
            }
            if (state.actionState_2 is Resource.NoAction) {
                Text(text = state.actionResult_2)
            }
            else {
                Text(text = state.getActionResult2())
            }
        }
    }


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


}