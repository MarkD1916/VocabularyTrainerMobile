package com.example.vocabularytrainer.domain.detail_group.use_case

import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class GetAllWords(
    private val repository: HomeRepository
) {
    fun execute(): Flow<List<WordEntity>> {
        return repository.getAllWords()
    }
}