package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.data.remote.auth.api.AuthApi
import com.example.vocabularytrainer.data.remote.auth.request.AccountRequest
import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.util.getAuthResponseFromServer
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String
    ): RegistrationEvent {
        return try {
            val response = authApi.registerUser(AccountRequest(email, password))
            getAuthResponseFromServer(response)
        } catch (e: Exception) {
            RegistrationEvent.Error(e.message!!)
        }
    }


}