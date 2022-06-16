package com.example.vocabularytrainer.data.remote.home.api

import com.example.vocabularytrainer.data.remote.home.response.GroupResponse
import com.example.vocabularytrainer.presentation.home.Resource
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET("mobile_api/group/get")
    suspend fun getAllGroup(): Response<GroupResponse>
}