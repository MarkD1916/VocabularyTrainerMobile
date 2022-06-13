package com.example.vocabularytrainer.data.remote.auth.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("auth_token") val token: String,
)
