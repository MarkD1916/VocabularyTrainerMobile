package com.example.vocabularytrainer.domain.auth.use_case

import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent

class LogoutUser (private val repository: AuthRepository) {
    suspend fun execute(): LoginEvent {
        return repository.logout()
    }
}