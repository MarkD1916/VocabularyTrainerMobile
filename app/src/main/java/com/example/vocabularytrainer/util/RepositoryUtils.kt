package com.example.vocabularytrainer.util

import com.androiddevs.ktornoteapp.data.remote.interceptors.Variables
import com.example.response.SimpleResponse
import com.example.vocabularytrainer.data.remote.auth.response.LoginResponse
import com.example.vocabularytrainer.data.remote.auth.response.RegisterResponse
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.presentation.home.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.io.IOException
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

//ToDo нужно как-то унифицировать эти функции
fun getRegisterResponseFromServer(response: Response<SimpleResponse>): AuthEvent {

    return if (response.isSuccessful) {
        RegistrationEvent.Success(response.body())
    } else {
        if (response.code() == 400) {
            RegistrationEvent.Error("A user with that username already exists.")
        } else {
            RegistrationEvent.Error(response.message())
        }
    }
}

fun getLoginResponseFromServer(response: Response<LoginResponse>): AuthEvent {
    val gson = Gson()
    val type = object : TypeToken<SimpleResponse>() {}.type
    if (response.isSuccessful) {
        return LoginEvent.SuccessLogin(response.body())
    }
    return if (response.code() == 409) {
        val errorResponse: SimpleResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
        LoginEvent.Error(errorResponse?.message ?: "")
    }
    else {
        LoginEvent.Error(response.message())
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

