package com.example.vocabularytrainer.data.remote.auth.request

import com.google.gson.annotations.SerializedName


data class AccountRequest(
    @SerializedName("username") val email: String,
    val password: String
)
