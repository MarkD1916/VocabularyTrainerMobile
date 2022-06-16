package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent


interface AuthRepository {
    suspend fun register(
        email: String,
        password: String
    ): RegistrationEvent

    suspend fun login(
        email: String,
        password: String
    ): LoginEvent

    suspend fun logout(): AuthEvent
}