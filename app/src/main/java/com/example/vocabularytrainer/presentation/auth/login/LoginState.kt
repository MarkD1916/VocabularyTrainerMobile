package com.example.vocabularytrainer.presentation.auth.login

import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult

data class LoginState(
    val email: String = "anon669@gmail.com",
    val emailError:String? = null,
    val password: String = "123456789",
    val passwordError: String? = null,
    val loginResponseResult: AuthResponseResult? = null
)