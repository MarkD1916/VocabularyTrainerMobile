package com.example.vocabularytrainer.domain.auth.use_case

import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent

class LoginUser(private val repository: AuthRepository) {
    suspend fun execute(password: String, email: String): LoginEvent {
        return repository.login(email, password)
    }
}