package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.data.remote.home.remote.api.HomeApi
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.Resource
import com.example.vocabularytrainer.util.safeCall
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {
    override suspend fun getAllGroupFromServer(): Resource<List<GroupResponse>> {
        return try {
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

    }

    override suspend fun insertGroup() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGroupFromBD(): List<Group> {
        TODO("Not yet implemented")
    }
}