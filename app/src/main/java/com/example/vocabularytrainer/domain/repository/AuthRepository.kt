package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.presentation.auth.registration.ValidationEvent


interface AuthRepository {
    suspend fun register(): ValidationEvent
}