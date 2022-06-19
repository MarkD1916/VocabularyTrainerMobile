package com.example.vocabularytrainer.data.repository

import com.androiddevs.ktornoteapp.data.remote.interceptors.Variables
import com.example.vocabularytrainer.data.local.home.dao.VocabularyDao
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.mapper.home.toGroup
import com.example.vocabularytrainer.data.mapper.home.toGroupEntity
import com.example.vocabularytrainer.data.remote.home.remote.api.HomeApi
import com.example.vocabularytrainer.data.remote.home.remote.request.GroupRequest
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.HomeEvent
import com.example.vocabularytrainer.presentation.home.LoadingType
import com.example.vocabularytrainer.presentation.home.Resource
import com.example.vocabularytrainer.util.networkBoundResource
import com.example.vocabularytrainer.util.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val dao: VocabularyDao
) : HomeRepository {
    private var curGroupResponse: Response<List<GroupResponse>>? = null

    override suspend fun getAllGroupFromServer(): Flow<Resource<List<GroupEntity>>> {
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
        val result =  networkBoundResource(
            query = {
                dao.selectAllGroups()
            },
            fetch = {
                getGroupFromServer()
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

    private suspend fun getGroupFromServer() {
        curGroupResponse = homeApi.getAllGroup()
    }

    override suspend fun postGroup(groupRequest: GroupRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun insertGroup(groupEntity: GroupEntity) {
        TODO("Not yet implemented")
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