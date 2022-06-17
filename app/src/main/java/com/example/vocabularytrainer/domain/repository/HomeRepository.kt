package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.home.Resource

interface HomeRepository {

    suspend fun getAllGroupFromServer(): Resource<List<GroupResponse>>

    suspend fun insertGroup()

    suspend fun getAllGroupFromBD(): List<Group>


}