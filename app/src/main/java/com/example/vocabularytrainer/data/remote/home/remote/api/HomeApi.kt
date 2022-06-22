package com.example.vocabularytrainer.data.remote.home.remote.api

import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.data.remote.home.remote.response.SimpleResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {
    @GET("mobile_api/group/get")
    suspend fun getAllGroup(): Response<List<GroupResponse>>

    @DELETE("/mobile_api/group/delete/{id}/")
    suspend fun deleteGroup(
        @Path("id") groupId: String
    ): Response<SimpleResponse>


}