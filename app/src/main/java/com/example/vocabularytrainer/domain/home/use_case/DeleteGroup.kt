package com.example.vocabularytrainer.domain.home.use_case

import com.example.vocabularytrainer.domain.repository.HomeRepository

class DeleteGroup( private val repository: HomeRepository) {
    suspend fun execute(groupId: String) {
        return repository.deleteGroup(groupId)
    }
}