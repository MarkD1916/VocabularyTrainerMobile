package com.example.vocabularytrainer.util

import com.androiddevs.ktornoteapp.data.remote.interceptors.Variables
import com.example.vocabularytrainer.data.remote.auth.response.LoginResponse
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.home.Resource
import retrofit2.Response
import java.util.concurrent.TimeoutException

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        if (!Variables.isNetworkConnected) {
            Resource.Error("No Internet Connection", data = null)
        }
        else if (e.cause is TimeoutException) {
            throw TimeoutException()
        }
        else {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}


fun getLogoutResponseFromServer(response: Response<LoginResponse>): AuthEvent {

    return if (response.isSuccessful) {
        AuthEvent.Success
    } else {
        if (response.code() == 400) {
            LoginEvent.Error(response.message())
        } else {
            LoginEvent.Error(response.message())
        }
    }
}

