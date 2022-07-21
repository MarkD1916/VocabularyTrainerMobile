package com.example.vocabularytrainer.domain.home.use_case

import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.Resource
import kotlinx.coroutines.flow.Flow

class GetAllGroup(
    private val repository: HomeRepository
) {
    fun execute(): Flow<Resource<List<GroupEntity>>> {
        return repository.fetchAllGroupFromServer()
    }
}