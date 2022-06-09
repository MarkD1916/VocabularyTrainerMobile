package com.example.vocabularytrainer.util

import com.example.vocabularytrainer.data.remote.auth.response.AuthResponse
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import retrofit2.Response


fun getAuthResponseFromServer(response: Response<AuthResponse>): RegistrationEvent {

    return if (response.isSuccessful) {
        RegistrationEvent.Success(response.body())
    } else {
        if(response.code()==400) {
            RegistrationEvent.Error("A user with that username already exists.")
        } else{
            RegistrationEvent.Error(response.message())
        }
    }
}

