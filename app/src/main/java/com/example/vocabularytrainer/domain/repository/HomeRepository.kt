package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.home.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getAllGroupFromServer(): Flow<Resource<List<GroupResponse>>>

    suspend fun insertGroup()

    suspend fun getAllGroupFromBD(): List<Group>


}