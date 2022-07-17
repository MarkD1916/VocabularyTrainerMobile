package com.example.vocabularytrainer.data.remote.auth.api

import com.example.response.SimpleResponse
import com.example.vocabularytrainer.data.remote.auth.request.LoginRequest
import com.example.vocabularytrainer.data.remote.auth.request.RegisterRequest
import com.example.vocabularytrainer.data.remote.auth.response.LoginResponse
import com.example.vocabularytrainer.data.remote.auth.response.RegisterResponse
import com.example.vocabularytrainer.data.remote.auth.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("register")
    suspend fun registerUser(@Body user: RegisterRequest): Response<SimpleResponse>

    @POST("login")
    suspend fun logInUser(@Body user: LoginRequest): Response<LoginResponse>

    @POST("/mobile/token/logout")
    suspend fun logOutUser(): Response<LoginResponse>

    @GET("secret")
    suspend fun getCurrentUser(): Response<UserResponse>
}