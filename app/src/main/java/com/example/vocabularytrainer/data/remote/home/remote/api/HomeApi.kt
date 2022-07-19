package com.example.vocabularytrainer.data.remote.home.remote.api

import com.example.vocabularytrainer.data.remote.home.remote.request.GroupRequest
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.data.remote.home.remote.response.SimpleResponse
import com.example.vocabularytrainer.data.remote.detail_group.remote.response.WordResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {
    // Group request
    @GET("getGroup")
    suspend fun getAllGroup(): Response<List<GroupResponse>>

    @DELETE("deleteGroup/{id}")
    suspend fun deleteGroup(
        @Path("id") groupId: String
    ): Response<SimpleResponse>

    @POST("postGroup")
    suspend fun postGroup(@Body group: GroupRequest): Response<SimpleResponse>

}