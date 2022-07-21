package com.example.vocabularytrainer.data.remote.detail_group.remote.response

data class WordResponse(
    val id: String,
    val date: Long,
    val groupId: String,
    val word: String,
    val translate: String,
    val transcription: String,
    val noteDescription: String,
    val imageUrl: String,
    val exampleForWord: List<String>
)
