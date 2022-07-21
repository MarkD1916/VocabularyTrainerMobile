package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.local.home.entity.relations.GroupWithWords
import com.example.vocabularytrainer.data.remote.detail_group.remote.request.WordRequest
import com.example.vocabularytrainer.data.remote.detail_group.remote.response.WordResponse
import com.example.vocabularytrainer.domain.repository.WordRepository
import com.example.vocabularytrainer.presentation.home.Resource
import kotlinx.coroutines.flow.Flow

class WordRepositoryImpl : WordRepository {

    override fun fetchWordByGroupFromServer(): Flow<Resource<List<WordEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun postWord(wordRequest: WordRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun insertWord(wordEntity: WordEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertWords(words: List<WordResponse>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWord(wordId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun syncWordInGroup(groupId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocallyDeletedWordID(deletedWordId: String) {
        TODO("Not yet implemented")
    }

    override fun getWordsByGroupFromServer(groupId: String): Flow<Resource<List<GroupWithWords>>> {
        TODO("Not yet implemented")
    }

    override fun getAllGroupFromBD(): Flow<List<WordEntity>> {
        TODO("Not yet implemented")
    }
}