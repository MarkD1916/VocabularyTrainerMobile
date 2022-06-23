package com.example.vocabularytrainer.data.remote.home.remote.api

import com.example.vocabularytrainer.data.remote.home.remote.request.GroupRequest
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.data.remote.home.remote.response.SimpleResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeApi {
    @GET("mobile_api/group/get")
    suspend fun getAllGroup(): Response<List<GroupResponse>>

    @DELETE("/mobile_api/group/delete/{id}/")
    suspend fun deleteGroup(
        @Path("id") groupId: String
    ): Response<SimpleResponse>

    @POST("/mobile_api/group/post/")
    suspend fun postGroup( @Body group: GroupRequest): Response<GroupResponse>
}