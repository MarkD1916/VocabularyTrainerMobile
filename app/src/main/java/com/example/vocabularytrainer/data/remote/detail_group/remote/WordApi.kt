package com.example.vocabularytrainer.data.remote.detail_group.remote

import com.example.vocabularytrainer.data.remote.detail_group.remote.response.WordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WordApi {
    @GET("getWords/{groupId}")
    suspend fun getWordByGroup(@Path("groupId") groupId: String): Response<List<WordResponse>>
}