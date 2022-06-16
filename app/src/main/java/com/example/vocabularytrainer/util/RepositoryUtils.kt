package com.example.vocabularytrainer.util

import com.example.vocabularytrainer.data.remote.auth.response.LoginResponse
import com.example.vocabularytrainer.data.remote.auth.response.RegisterResponse
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import retrofit2.Response

//ToDo нужно как-то унифицировать эти функции
fun getRegisterResponseFromServer(response: Response<RegisterResponse>): AuthEvent {

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

    return if (response.isSuccessful) {
        LoginEvent.SuccessLogin(response.body())
    } else {
        if (response.code() == 400) {
            LoginEvent.Error(response.message())
        } else {
            LoginEvent.Error(response.message())
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

