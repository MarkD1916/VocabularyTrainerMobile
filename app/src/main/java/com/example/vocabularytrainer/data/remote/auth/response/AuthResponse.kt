package com.example.vocabularytrainer.data.remote.auth.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("username") val email: String,
    val password: String
)
