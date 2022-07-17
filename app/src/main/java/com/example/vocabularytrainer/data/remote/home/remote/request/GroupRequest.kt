package com.example.vocabularytrainer.data.remote.home.remote.request

import com.google.gson.annotations.SerializedName

data class GroupRequest(
    val group_name: String,
    val date: Long = System.currentTimeMillis(),
    val owners: List<String>
)
