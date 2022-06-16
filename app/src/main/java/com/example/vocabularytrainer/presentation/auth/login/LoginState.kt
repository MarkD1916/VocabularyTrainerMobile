package com.example.vocabularytrainer.presentation.auth.login

import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult

data class LoginState(
    val email: String = "",
    val emailError:String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val loginResponseResult: AuthResponseResult? = null
)