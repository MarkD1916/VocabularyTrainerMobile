package com.example.vocabularytrainer.data.remote.home.remote.response

import com.google.gson.annotations.SerializedName

data class GroupResponse(
    val id: String,
    @SerializedName("groupName")val name: String,
    @SerializedName("date") val date: Long,
    val owners: List<String>
)
