package com.example.vocabularytrainer.presentation.auth

interface AuthEvent {
    object Loading : AuthEvent
    object Success: AuthEvent

    object NoInternetConnection: AuthEvent

    data class OnEmailEnter(
        val email: String = ""
    ) : AuthEvent

    data class OnPasswordEnter(
        val password: String = ""
    ) : AuthEvent
}