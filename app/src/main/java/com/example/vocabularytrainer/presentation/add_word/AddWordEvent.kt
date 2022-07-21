package com.example.vocabularytrainer.presentation.add_word

sealed class AddWordEvent {
    data class OnNewWordNativeEnter(val newWordNative: String) : AddWordEvent()
    data class OnNewWordTranslateEnter(val newWordTranslate: String) : AddWordEvent()
}
