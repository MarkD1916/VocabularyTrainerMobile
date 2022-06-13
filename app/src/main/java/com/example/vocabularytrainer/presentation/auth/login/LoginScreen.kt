package com.example.vocabularytrainer.presentation.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.presentation.auth.login.LoginViewModel
import com.example.vocabularytrainer.presentation.components.LoadAnimation
import com.example.vocabularytrainer.presentation.components.LoadingAnimationType
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.flow.collectIndexed

@Composable
fun AuthScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {

        loginViewModel.uiEvent?.collectIndexed { index, event ->

            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }

        }

    }

    Box(modifier=Modifier.fillMaxSize()) {
        LoadAnimation(modifier =
        Modifier
            .align(Alignment.Center)
            .size(300.dp),
            radius = 200f,
            stroke = 27f,
            animationType = LoadingAnimationType.Screen
        )
//        Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
    }

}