package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.data.remote.auth.api.AuthApi
import com.example.vocabularytrainer.data.remote.auth.request.LoginRequest
import com.example.vocabularytrainer.data.remote.auth.request.RegisterRequest
import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.util.getLoginResponseFromServer
import com.example.vocabularytrainer.util.getRegisterResponseFromServer
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String
    ): RegistrationEvent {
        return try {
            val response = authApi.registerUser(RegisterRequest(email, password))
            getRegisterResponseFromServer(response)
        } catch (e: Exception) {
            RegistrationEvent.Error(e.message!!)
        }
    }

    override suspend fun login(email: String, password: String): LoginEvent {
        return try {
            val response = authApi.loginUser(LoginRequest(email, password))
            getLoginResponseFromServer(response)
        } catch (e: Exception) {
            LoginEvent.Error(e.message!!)
        }
    }

    override suspend fun logout(token: String): LoginEvent {
        TODO("Not yet implemented")
    }
}