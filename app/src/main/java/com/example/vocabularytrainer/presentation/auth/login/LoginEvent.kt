package com.example.vocabularytrainer.presentation.auth.login

import com.example.vocabularytrainer.data.remote.auth.response.LoginResponse
import com.example.vocabularytrainer.presentation.auth.AuthEvent

sealed class LoginEvent : AuthEvent {
    data class SuccessLogin(
        val result: LoginResponse? = null
    ) : LoginEvent()

    data class Error(val message: String) : LoginEvent()

    object Login: AuthEvent

    object LogOut : LoginEvent()
}