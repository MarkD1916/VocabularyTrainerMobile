package com.example.vocabularytrainer.domain.auth.use_case

import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent

class LoginUser(private val repository: AuthRepository) {
    suspend fun execute(password: String, email: String): AuthEvent {
        return repository.login(email, password)
    }
}