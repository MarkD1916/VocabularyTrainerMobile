package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.presentation.home.HomeEvent

interface HomeRepository {

    suspend fun getAllGroup(): HomeEvent
}