package com.example.vocabularytrainer.data.repository

import com.example.response.SimpleResponse
import com.example.vocabularytrainer.data.remote.auth.api.AuthApi
import com.example.vocabularytrainer.data.remote.auth.request.LoginRequest
import com.example.vocabularytrainer.data.remote.auth.request.RegisterRequest
import com.example.vocabularytrainer.domain.repository.AuthRepository
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.util.getLogoutResponseFromServer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    val gson = Gson()
    val type = object : TypeToken<SimpleResponse>() {}.type

    override suspend fun register(
        email: String,
        password: String
    ): AuthEvent {
        return try {
            val response = authApi.registerUser(RegisterRequest(email, password))
            if (response.isSuccessful) {
                return RegistrationEvent.Success(response.body())
            }
            return if (response.code() == 409) {
                val errorResponse: SimpleResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                LoginEvent.Error(errorResponse?.message ?: "")
            } else {
                RegistrationEvent.Error(response.message())
            }

        } catch (e: Exception) {
            RegistrationEvent.Error(e.message!!)
        }
    }

    override suspend fun login(email: String, password: String): AuthEvent {
        return try {
            val response = authApi.logInUser(LoginRequest(email, password))

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
                if (response.code() == 401) {
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