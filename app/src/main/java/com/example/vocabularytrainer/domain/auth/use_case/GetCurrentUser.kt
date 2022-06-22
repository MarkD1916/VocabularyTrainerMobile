package com.example.vocabularytrainer.domain.auth.use_case

import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.AuthEvent

class GetCurrentUser (private val repository: AuthRepository) {
    suspend fun execute(): AuthEvent {
        return repository.getCurrentUser()
    }
}