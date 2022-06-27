package com.example.vocabularytrainer.presentation.detail_group

import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.home.LoadingType

sealed class DetailGroupEvent() {

    data class OnNewWordNativeEnter(val newWordNative: String) : DetailGroupEvent()
    data class OnNewWordTranslateEnter(val newWordTranslate: String) : DetailGroupEvent()

    object GetAllWords : DetailGroupEvent() {
        var loadingType: LoadingType<List<Group>> = LoadingType.FullScreenLoading()
    }
}
