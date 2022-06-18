package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.data.local.home.dao.VocabularyDao
import com.example.vocabularytrainer.data.remote.home.remote.api.HomeApi
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.Resource
import com.example.vocabularytrainer.util.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi,
    private val dao: VocabularyDao
) : HomeRepository {
    override suspend fun getAllGroupFromServer(): Flow<Resource<List<GroupResponse>>> = flow {
        val result = try {
            safeCall {
                val response = homeApi.getAllGroup()
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error(response.message())
                }
            }
        }
        catch(e: Exception) {
            Resource.Error(e.message!!)
        }
        emit(result)

    }

    override suspend fun insertGroup() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGroupFromBD(): List<Group> {
        TODO("Not yet implemented")
    }
}