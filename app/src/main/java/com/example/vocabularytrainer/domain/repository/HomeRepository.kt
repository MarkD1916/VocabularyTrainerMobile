package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.data.remote.home.response.GroupResponse
import com.example.vocabularytrainer.presentation.home.HomeEvent
import com.example.vocabularytrainer.presentation.home.Resource

interface HomeRepository {

    suspend fun getAllGroup(): Resource<GroupResponse>
}