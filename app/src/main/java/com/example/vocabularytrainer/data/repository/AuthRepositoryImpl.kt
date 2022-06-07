package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.registration.ValidationEvent
import java.lang.Exception


class AuthRepositoryImpl: AuthRepository {

    override suspend fun register(): ValidationEvent {
        return try {
            ValidationEvent.Success
        } catch (e: Exception) {
            ValidationEvent.Error(e.message!!)
        }
    }


}