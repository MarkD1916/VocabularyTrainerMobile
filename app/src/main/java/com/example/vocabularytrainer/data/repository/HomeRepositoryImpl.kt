package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.data.remote.home.api.HomeApi
import com.example.vocabularytrainer.data.remote.home.response.GroupResponse
import com.example.vocabularytrainer.domain.home.use_case.GetAllGroup
import com.example.vocabularytrainer.domain.repository.HomeRepository
import com.example.vocabularytrainer.presentation.home.HomeEvent
import com.example.vocabularytrainer.presentation.home.Resource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : HomeRepository {
    override suspend fun getAllGroup(): Resource<GroupResponse> {
        return try {
            val response = homeApi.getAllGroup()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
                }
            }
         catch (e: Exception) {
             Resource.Error(e.message!!)
        }
    }
}