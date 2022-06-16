package com.example.vocabularytrainer.data.remote.home.response

import com.google.gson.annotations.SerializedName

data class GroupResponse(
    val id: String,
    @SerializedName("group_name")val name: String
)
