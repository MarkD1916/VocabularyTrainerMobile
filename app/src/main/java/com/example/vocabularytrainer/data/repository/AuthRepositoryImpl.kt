package com.example.vocabularytrainer.data.repository

import com.example.vocabularytrainer.data.remote.auth.api.AuthApi
import com.example.vocabularytrainer.data.remote.auth.request.LoginRequest
import com.example.vocabularytrainer.data.remote.auth.request.RegisterRequest
import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.util.getLoginResponseFromServer
import com.example.vocabularytrainer.util.getLogoutResponseFromServer
import com.example.vocabularytrainer.util.getRegisterResponseFromServer
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String
    ): AuthEvent {
        return try {
            val response = authApi.registerUser(RegisterRequest(email, password))
            getRegisterResponseFromServer(response)
        } catch (e: Exception) {
            RegistrationEvent.Error(e.message!!)
        }
    }

    override suspend fun login(email: String, password: String): AuthEvent {
        return try {
            val response = authApi.logInUser(LoginRequest(email, password))
            getLoginResponseFromServer(response)
        } catch (e: Exception) {
            LoginEvent.Error(e.message!!)
        }
    }

    override suspend fun logout(): AuthEvent {
        return try {
            val response = authApi.logOutUser()
            getLogoutResponseFromServer(response)
        } catch (e: Exception) {
            LoginEvent.Error(e.message!!)
        }
    }

    override suspend fun getCurrentUser(): AuthEvent {
        return try {
            val response = authApi.getCurrentUser()
            return if (response.isSuccessful) {
                LoginEvent.SetUserId(response.body())
            } else {
                if (response.code() == 400) {
                    LoginEvent.Error(response.message())
                } else {
                    LoginEvent.Error(response.message())
                }
            }
        } catch (e: Exception) {
            LoginEvent.Error(e.message!!)
        }
    }
}