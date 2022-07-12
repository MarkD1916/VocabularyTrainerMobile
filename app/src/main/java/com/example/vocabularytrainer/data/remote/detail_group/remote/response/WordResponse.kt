package com.example.vocabularytrainer.data.remote.detail_group.remote.response

import com.google.gson.annotations.SerializedName

data class WordResponse(
    @SerializedName("group_slug")val groupId: String,
    @SerializedName("slug")val id: String,
    val word: String,
    val translate: String,
    val group_name: String,
    val transcription: String
)
