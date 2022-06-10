package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent


interface AuthRepository {
    suspend fun register(email: String,
                         password: String): RegistrationEvent
    suspend fun getCountryFlag(email: String,
                         password: String): RegistrationEvent
}