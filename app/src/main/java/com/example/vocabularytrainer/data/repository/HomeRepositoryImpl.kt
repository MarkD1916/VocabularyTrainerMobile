package com.example.vocabularytrainer.data.repository

import android.util.Log
import com.androiddevs.ktornoteapp.data.remote.interceptors.Variables
import com.example.vocabularytrainer.data.local.home.dao.VocabularyDao
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.local.home.entity.LocallyDeletedGroupID
import com.example.vocabularytrainer.data.mapper.home.toGroupEntity
import com.example.vocabularytrainer.data.remote.home.remote.api.HomeApi
import com.example.vocabularytrainer.data.remote.home.remote.request.GroupRequest
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.Resource
import com.example.vocabularytrainer.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val dao: VocabularyDao
) : HomeRepository {
    private var curGroupResponse: Response<List<GroupResponse>>? = null

    override fun getAllGroupFromServer(): Flow<Resource<List<GroupEntity>>> {
//        val result = try {
//            safeCall {
//                val response = homeApi.getAllGroup()
//                if (response.isSuccessful && response.body() != null) {
//                    insertGroups(response.body()!!)
//                    Resource.Success(response.body()!!)
//                } else {
//                    Resource.Error(response.message())
//                }
//            }
//        }
//        catch(e: Exception) {
//            Resource.Error(e.message!!)
//        }
//        emit(result)
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

    override suspend fun syncGroups() {
        val locallyDeletedGroupIDs = dao.getAllLocallyDeletedGroupId()
        locallyDeletedGroupIDs.forEach { id -> deleteGroup(id.deletedGroupId) }

        val unsyncedGroups = dao.getAllUnsyncedGroups()

        unsyncedGroups.forEach { group -> insertGroup(group) }

        curGroupResponse = homeApi.getAllGroup()
        curGroupResponse?.body()?.let { groups ->
            dao.deleteAllGroups()

            insertGroups(groups.onEach { group ->
                group.toGroupEntity()
            })
        }
    }

    override suspend fun deleteLocallyDeletedGroupID(deletedGroupID: String) {
        dao.deleteLocallyDeletedNoteID(deletedGroupID)
    }

    override suspend fun postGroup(groupRequest: GroupRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun insertGroup(groupEntity: GroupEntity) {
        dao.insertGroup(groupEntity)
    }

    override suspend fun getAllGroupFromBD(): List<Group> {
        TODO("Not yet implemented")
    }

    override suspend fun insertGroups(groups: List<GroupResponse>) {
        groups.forEach {
            dao.insertGroup(it.toGroupEntity())
        }
    }
}