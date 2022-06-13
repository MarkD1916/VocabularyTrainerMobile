package com.example.vocabularytrainer.data.remote.auth.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("username") val email: String,
    val password: String
)
