package com.example.vocabularytrainer.data.remote.home.remote.request

import com.google.gson.annotations.SerializedName

data class GroupRequest(
    val id: String,
    val groupName: String,
    val date: Long = System.currentTimeMillis(),
    val owners: List<String>
)
