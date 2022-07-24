package com.example.vocabularytrainer.data.remote.detail_group.remote.request

data class WordRequest(
    val id: String,
    val date: Long = System.currentTimeMillis(),
    val groupId: String,
    val word: String,
    val translate: String,
    val transcription: String = "",
    val noteDescription: String = "",
    val imageUrl: List<String> = listOf(),
    val exampleForWord: List<String> = listOf()
)
