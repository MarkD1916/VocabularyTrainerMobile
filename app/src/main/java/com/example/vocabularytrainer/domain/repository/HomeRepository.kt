package com.example.vocabularytrainer.domain.repository

import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.local.home.entity.relations.GroupWithWords
import com.example.vocabularytrainer.data.remote.home.remote.request.GroupRequest
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.home.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    // Work with GROUPS
    fun getAllGroupFromServer(): Flow<Resource<List<GroupEntity>>>

    suspend fun postGroup(groupRequest: GroupRequest)

    suspend fun insertGroup(groupEntity: GroupEntity)

    suspend fun insertGroups(groups: List<GroupResponse>)

    suspend fun getAllGroupFromBD(): List<Group>

    suspend fun deleteGroup(groupId: String)

    suspend fun syncGroups()

    suspend fun deleteLocallyDeletedGroupID(deletedGroupID: String)

    //Work with WORDS
    fun getWordsByGroupFromServer(groupId: String): Flow<Resource<List<GroupWithWords>>>

    suspend fun syncWordsByGroup(groupId: String)

    fun getAllWords(): Flow<List<WordEntity>>

//    suspend fun getAllWordsByGroupFromBD(): List<Group>
}