package com.example.vocabularytrainer.data.remote.home.remote.api

import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    @GET("mobile_api/group/get")
    suspend fun getAllGroup(): Response<List<GroupResponse>>
}