package com.example.vocabularytrainer.presentation.auth.login

import com.example.vocabularytrainer.data.remote.auth.response.LoginResponse
import com.example.vocabularytrainer.presentation.auth.registration.GeneralEvent

sealed class LoginEvent: GeneralEvent {

    data class Success(
        val result: LoginResponse? = null
    ) : LoginEvent()

    data class Error(val message: String) : LoginEvent()
}