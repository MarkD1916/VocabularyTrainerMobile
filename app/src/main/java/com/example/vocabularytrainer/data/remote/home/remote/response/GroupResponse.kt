package com.example.vocabularytrainer.data.remote.home.remote.response

import com.google.gson.annotations.SerializedName

data class GroupResponse(
    val id: String,
    @SerializedName("group_name")val name: String,
    @SerializedName("user") val userName: String
)
