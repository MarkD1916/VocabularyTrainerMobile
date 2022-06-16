package com.example.vocabularytrainer.domain.home.use_case

import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.presentation.home.HomeEvent

class GetAllGroup(
    private val repository: HomeRepository
) {
    suspend fun execute(): HomeEvent {
        return repository.getAllGroup()
    }
}