package com.example.vocabularytrainer.data.remote.detail_group.remote.response

data class WordResponse(
    val groupId: String,
    val id: String,
    val word: String,
    val translate: String,
    val transcription: String
)
