package com.example.vocabularytrainer.presentation.add_word

data class AddWordState(
    val word: String = "",
    val translate: String = "",
    val transcription: String = "",
    val note: String = "",
    val imageUrl: String = "",
    val exampleWithWord: String = ""
)
