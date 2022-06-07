package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.presentation.auth.registration.ValidationEvent
import java.lang.Exception


class AuthRepositoryImpl: AuthRepository {

    override suspend fun register(): RegistrationEvent {
        return try {
            RegistrationEvent.Success
        } catch (e: Exception) {
            RegistrationEvent.Error(e.message!!)
        }
    }


}