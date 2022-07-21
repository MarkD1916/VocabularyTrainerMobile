package com.example.vocabularytrainer.domain.home.use_case

import com.example.vocabularytrainer.domain.repository.HomeRepository

class SyncWords(
    private val repository: HomeRepository
) {
    suspend fun execute() {
        return repository.syncAllWord()
    }
}