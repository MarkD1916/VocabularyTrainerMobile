package com.example.vocabularytrainer.data.repository

import android.util.Log
import com.androiddevs.ktornoteapp.data.remote.interceptors.TokenInterceptor
import com.example.vocabularytrainer.data.preferences.AuthPreferenceImpl
import com.example.vocabularytrainer.data.remote.*
import com.example.vocabularytrainer.data.remote.auth.api.AuthApi
import com.example.vocabularytrainer.data.remote.auth.response.LoginResponse
import com.example.vocabularytrainer.data.remote.auth.response.UserResponse
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AuthRepositoryImplTest {

    private lateinit var repository: AuthRepositoryImpl
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var authApi: AuthApi

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var incorrectEmail: String
    private lateinit var incorrectPassword: String


    @Before
    fun setUp() {
        val preferences = mockk<AuthPreferenceImpl>(relaxed = true)
        every { preferences.getStoredToken() } returns "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VycyIsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwODAiLCJleHAiOjE2ODk4NTM4MDAsInVzZXJJZCI6IjYyZDJmMjZjNWVjMTlkMGI5NDExYTg1MSJ9.AOst5xNHYDxsKBcRTr3H1F98JE1n_PBvxKV6tMIfSsg"

        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(preferences))
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()

        authApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(AuthApi::class.java)

        repository = AuthRepositoryImpl(
            authApi = authApi
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Get current user, valid response, return userId in UserResponse`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validUserResponse)
        )
        val result = repository.getCurrentUser()
        Truth.assertThat(result)
            .isEqualTo(LoginEvent.SetUserId(UserResponse(id = "62d14c7f4156f359e73e6004")))
    }

    @Test
    fun `Get current user, not valid response, return Error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody(invalidUserResponse)
        )
        val result = repository.getCurrentUser()
        Truth.assertThat(result)
            .isEqualTo(LoginEvent.Error("Client Error"))
    }

    @Test
    fun `Login user, valid response`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validLoginResponse)
        )

        val result = repository.login("anon669@gmail.com", "123456789")
        Truth.assertThat(result).isEqualTo(
            LoginEvent.SuccessLogin(
                LoginResponse(
                    token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VycyIsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwODAiLCJleHAiOjE2ODk4NTM4MDAsInVzZXJJZCI6IjYyZDJmMjZjNWVjMTlkMGI5NDExYTg1MSJ9.AOst5xNHYDxsKBcRTr3H1F98JE1n_PBvxKV6tMIfSsg",
                    mainGroupId = "62d2f26c5ec19d0b9411a852"
                )
            )
        )

    }


    @Test
    fun `Login user, invalid response`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody(invalidLoginResponse)
        )

        val result = repository.login("anon669@gmail.com", "123456789")
        Truth.assertThat(result).isEqualTo(
            LoginEvent.Error("Server Error")
        )

    }

    @Test
    fun `Login user, malformed response Email`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(409)
                .setBody(invalidEmailResponse)
        )

        val result = repository.login("anon669@gmail.co", "123456789")
        Truth.assertThat(result).isEqualTo(
            LoginEvent.Error("Incorrect username or password")
        )

    }

    @Test
    fun `Login user, malformed response Password`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(409)
                .setBody(invalidPasswordResponse)
        )

        val result = repository.login("anon669@gmail.com", "123")
        Truth.assertThat(result).isEqualTo(
            LoginEvent.Error("Incorrect username or password")
        )

    }
}