package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.data.remote.home.api.HomeApi
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.HomeEvent
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {
    override suspend fun getAllGroup(): HomeEvent {
        TODO("Not yet implemented")
    }
}