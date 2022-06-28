package com.example.vocabularytrainer.presentation.auth.login

import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult
import com.vmakd1916gmail.com.core.util.UiText

data class LoginState(
    val email: String = "anon669@gmail.com",
    val emailError: UiText.StringResource? = null,
    val password: String = "123456789",
    val passwordError: UiText.StringResource? = null,
    val loginResponseResult: AuthResponseResult? = null
)