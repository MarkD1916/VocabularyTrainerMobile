package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.presentation.auth.AuthEvent


interface AuthRepository {
    suspend fun register(
        email: String,
        password: String
    ): AuthEvent

    suspend fun login(
        email: String,
        password: String
    ): AuthEvent

    suspend fun logout(): AuthEvent

    suspend fun getCurrentUser(): AuthEvent
}