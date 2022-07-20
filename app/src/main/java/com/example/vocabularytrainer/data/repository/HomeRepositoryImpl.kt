package com.example.vocabularytrainer.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.androiddevs.ktornoteapp.data.remote.interceptors.Variables
import com.example.vocabularytrainer.data.local.home.dao.VocabularyDao
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.local.home.entity.LocallyDeletedGroupID
import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.local.home.entity.relations.GroupWithWords
import com.example.vocabularytrainer.data.mapper.home.toGroupEntity
import com.example.vocabularytrainer.data.mapper.home.toGroupRequest
import com.example.vocabularytrainer.data.mapper.home.toWordEntity
import com.example.vocabularytrainer.data.preferences.AuthPreferenceImpl
import com.example.vocabularytrainer.data.preferences.HomePreferenceImpl
import com.example.vocabularytrainer.data.remote.detail_group.remote.WordApi
import com.example.vocabularytrainer.data.remote.home.remote.api.HomeApi
import com.example.vocabularytrainer.data.remote.home.remote.request.GroupRequest
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.data.remote.detail_group.remote.response.WordResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.domain.repository.SyncController
import com.example.vocabularytrainer.presentation.home.Resource
import com.example.vocabularytrainer.util.Constants.MAIN_GROUP_NAME
import com.example.vocabularytrainer.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val wordApi: WordApi,
    private val dao: VocabularyDao,
    private val authSharedPreferences: AuthPreferenceImpl
) : HomeRepository, SyncController {
    private var curGroupResponse: Response<List<GroupResponse>>? = null
    private var curWordResponse: Response<List<WordResponse>>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncGroupsAndWords() {
        syncGroups()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAllGroupFromServer(): Flow<Resource<List<GroupEntity>>> {
        val result = networkBoundResource(
            query = {
                dao.selectAllGroups()
            },
            fetch = {
                syncGroups()
                curGroupResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let {
                    insertGroups(it)
                }
            },
            shouldFetch = {
                Variables.isNetworkConnected
            }
        )

        return result
    }

    override suspend fun deleteGroup(groupId: String) {
        val response = try {
            homeApi.deleteGroup(groupId)
        } catch (e: Exception) {
            null
        }
        dao.deleteGroupById(groupId)
        if (response == null || !response.isSuccessful) {
            dao.insertLocallyDeletedGroupID(LocallyDeletedGroupID(groupId))
        } else {
            deleteLocallyDeletedGroupID(groupId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncGroups() {
        val locallyDeletedGroupIDs = dao.getAllLocallyDeletedGroupId()
        locallyDeletedGroupIDs.forEach { id -> deleteGroup(id.deletedGroupId) }

        val unsyncedGroups = dao.getAllUnsyncedGroups()

        unsyncedGroups.forEach { group -> postGroup(group.toGroupRequest(authSharedPreferences.getUserId())) }

        curGroupResponse = homeApi.getAllGroup()
        curGroupResponse?.body()?.let { groups ->
            dao.deleteAllGroups()

            insertGroups(groups.onEach { group ->
                group.toGroupEntity()
            })
        }
    }

    override fun getAllWords(): Flow<List<WordEntity>> {
        return dao.getAllWords()
    }

    override suspend fun syncWordsByGroup(groupId: String) {
        Log.d("TESTS", "syncWordsByGroup: $groupId")
        if (Variables.isNetworkConnected) {
            val unsyncedWords = dao.getAllUnsyncedWords()


            curWordResponse = wordApi.getWordByGroup(groupId)
            curWordResponse?.body()?.let { words ->
                dao.deleteAllWords()

                insertWords(words.onEach { word ->
                    Log.d("TESTS", "syncWordsByGroup: $word")
                    word.toWordEntity()
                })
            }
        }
    }

    override suspend fun deleteLocallyDeletedGroupID(deletedGroupID: String) {
        dao.deleteLocallyDeletedNoteID(deletedGroupID)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun postGroup(groupRequest: GroupRequest) {
        val response = try {
            homeApi.postGroup(groupRequest)
        } catch (e: Exception) {
            null
        }
        if (response != null && response.isSuccessful) {
            dao.insertGroup(groupRequest.toGroupEntity().apply {
                isSync = true
            })
        } else {
            dao.insertGroup(groupRequest.toGroupEntity())
        }
    }

    override suspend fun insertGroup(groupEntity: GroupEntity) {
        dao.insertGroup(groupEntity)
    }

    override suspend fun getAllGroupFromBD(): List<Group> {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertGroups(groups: List<GroupResponse>) {
        groups.forEach {
            val groupEntity = it.toGroupEntity()
            dao.insertGroup(groupEntity)
        }
    }

    suspend fun insertWords(words: List<WordResponse>) {
        words.forEach {
            dao.insertWord(it.toWordEntity())
        }
    }

    override fun getWordsByGroupFromServer(groupId: String): Flow<Resource<List<GroupWithWords>>> {
        val result = networkBoundResource(
            query = {
                dao.getGroupWithWords(groupId)
            },
            fetch = {
                syncWordsByGroup(groupId)
                curWordResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let {
                    insertWords(it)
                }
            },
            shouldFetch = {
                Variables.isNetworkConnected
            }
        )

        return result
    }
}