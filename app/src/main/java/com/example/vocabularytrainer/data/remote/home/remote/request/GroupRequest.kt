package com.example.vocabularytrainer.data.remote.home.remote.request

import com.google.gson.annotations.SerializedName

data class GroupRequest(
    val group_name: String,
    val user: String,
    @SerializedName("slug")val id: String,
)
