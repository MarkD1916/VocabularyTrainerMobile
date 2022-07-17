package com.example.vocabularytrainer.data.remote.auth.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: String,
    val mainGroupId: String
)
