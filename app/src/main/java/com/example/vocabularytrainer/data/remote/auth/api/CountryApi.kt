package com.example.vocabularytrainer.data.remote.auth.api

import com.example.vocabularytrainer.data.remote.auth.response.AuthResponse
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {
    @GET("/all")
    suspend fun getAllCountry(): Response<AuthResponse>
}