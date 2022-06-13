package com.example.vocabularytrainer.data.remote.auth.api

import com.example.vocabularytrainer.data.remote.auth.request.LoginRequest
import com.example.vocabularytrainer.data.remote.auth.request.RegisterRequest
import com.example.vocabularytrainer.data.remote.auth.response.LoginResponse
import com.example.vocabularytrainer.data.remote.auth.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/mobile/users/")
    suspend fun registerUser(@Body user: RegisterRequest): Response<RegisterResponse>
//
    @POST("/mobile/token/login")
    suspend fun loginUser(@Body user: LoginRequest): Response<LoginResponse>

//    @POST("auth/token/logout/")
//    suspend fun logoutUser(): Response<Any>
}