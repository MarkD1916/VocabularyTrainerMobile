package com.example.vocabularytrainer.domain.detail_group.use_case

import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.local.home.entity.relations.GroupWithWords
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.Resource
import kotlinx.coroutines.flow.Flow

class GetWordsByGroup(
    private val repository: HomeRepository
) {
    fun execute(groupId: String): Flow<Resource<List<GroupWithWords>>> {
        return repository.getAllWordsByGroupFromServer(groupId)
    }
}