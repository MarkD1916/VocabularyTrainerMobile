package com.example.vocabularytrainer.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.presentation.auth.login.LoginViewModel
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationViewModel
import com.example.vocabularytrainer.presentation.components.LoadAnimation

@Composable
fun AuthScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Box(modifier=Modifier.fillMaxSize()) {
        LoadAnimation(modifier =
        Modifier
            .background(Color.Green)
            .align(Alignment.Center)
            .size(300.dp)
        )
    }

}