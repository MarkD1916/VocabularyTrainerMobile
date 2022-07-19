package com.example.vocabularytrainer.presentation.add_word

import com.example.vocabularytrainer.presentation.detail_group.DetailGroupEvent

sealed class AddWordEvent {
    data class OnNewWordNativeEnter(val newWordNative: String) : AddWordEvent()
    data class OnNewWordTranslateEnter(val newWordTranslate: String) : AddWordEvent()
}
