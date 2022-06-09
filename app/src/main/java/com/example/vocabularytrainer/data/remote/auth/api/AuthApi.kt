package com.example.vocabularytrainer.data.remote.auth.api

import com.example.vocabularytrainer.data.remote.auth.request.AccountRequest
import com.example.vocabularytrainer.data.remote.auth.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/mobile/users/")
    suspend fun registerUser(@Body user: AccountRequest): Response<AuthResponse>
//
//    @POST("auth/token/login/")
//    suspend fun authUser(@Body user: UserResponse): Response<TokenResponse>
//
//    @POST("auth/token/logout/")
//    suspend fun logoutUser(): Response<Any>
}