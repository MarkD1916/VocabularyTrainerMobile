package com.example.vocabularytrainer.domain.auth.use_case

import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.presentation.auth.registration.ValidationEvent

class RegisterUser(
    private val repository: AuthRepository
) {
    suspend fun execute(password: String, email: String): RegistrationEvent {
        return repository.register()
    }
}