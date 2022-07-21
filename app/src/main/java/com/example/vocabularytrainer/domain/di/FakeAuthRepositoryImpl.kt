package com.example.vocabularytrainer.domain.di

import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.domain.repository.AuthRepository

class FakeAuthRepositoryImpl : AuthRepository {
    override suspend fun register(email: String, password: String): AuthEvent {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): AuthEvent {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): AuthEvent {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): AuthEvent {
        TODO("Not yet implemented")
    }
}
