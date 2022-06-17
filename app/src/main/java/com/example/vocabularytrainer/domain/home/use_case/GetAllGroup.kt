package com.example.vocabularytrainer.domain.home.use_case

import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.Resource

class GetAllGroup(
    private val repository: HomeRepository
) {
    suspend fun execute():  Resource<List<GroupResponse>> {
        return repository.getAllGroupFromServer()
    }
}