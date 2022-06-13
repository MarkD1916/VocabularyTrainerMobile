package com.example.vocabularytrainer.presentation.auth.login

import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult

data class LoginState(
    val email: String,
    val password: String,
    val loginResponseResult: AuthResponseResult? = null
)