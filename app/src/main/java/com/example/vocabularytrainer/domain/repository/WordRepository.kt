package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.local.home.entity.relations.GroupWithWords
import com.example.vocabularytrainer.data.remote.detail_group.remote.request.WordRequest
import com.example.vocabularytrainer.data.remote.detail_group.remote.response.WordResponse
import com.example.vocabularytrainer.presentation.home.Resource
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    fun fetchWordByGroupFromServer(): Flow<Resource<List<WordEntity>>>

    suspend fun postWord(wordRequest: WordRequest)

    suspend fun insertWord(wordEntity: WordEntity)

    suspend fun insertWords(words: List<WordResponse>)

    suspend fun deleteWord(wordId: String)

    suspend fun syncWordInGroup(groupId: String)

    suspend fun deleteLocallyDeletedWordID(deletedWordId: String)

    fun getWordsByGroupFromServer(groupId: String): Flow<Resource<List<GroupWithWords>>>

    fun getAllGroupFromBD(): Flow<List<WordEntity>>

}