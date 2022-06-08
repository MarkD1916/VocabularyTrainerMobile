package com.androiddevs.ktornoteapp.data.remote.interceptors

import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.util.Constants.IGNORE_AUTH_URL
import com.example.vocabularytrainer.util.Constants.TOKEN_HEADER_NAME
import com.example.vocabularytrainer.util.Constants.getTokenValue
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val authPreference: AuthPreference
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val token = authPreference.getStoredToken()
        if (request.url.encodedPath in IGNORE_AUTH_URL) {
            return chain.proceed(request)
        }
        return getResponse(token, request, chain)
    }


    private fun getResponse(token: String, request: Request, chain: Interceptor.Chain): Response {
        return if (token != "") {
            val newRequest =
                request.newBuilder().addHeader(TOKEN_HEADER_NAME, getTokenValue(token))
                    .method(request.method, request.body)
                    .build()
            chain.proceed(newRequest)
        } else {
            val newRequest =
                request.newBuilder()
                    .method(request.method, request.body)
                    .build()
            chain.proceed(newRequest)
        }
    }
}