package com.example.vocabularytrainer.presentation.detail_group

import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.home.LoadingType

sealed class DetailGroupEvent() {

    data class OnNewWordNativeEnter(val newWordNative: String) : DetailGroupEvent()
    data class OnNewWordTranslateEnter(val newWordTranslate: String) : DetailGroupEvent()

    object GetAllWordsByGroup : DetailGroupEvent() {
        var loadingType: LoadingType<List<Group>> = LoadingType.FullScreenLoading()
    }

    object GetAllWordsByMainGroup : DetailGroupEvent() {
        var loadingType: LoadingType<List<Group>> = LoadingType.FullScreenLoading()
    }

    data class  OnToggleClick(val wordId: String): DetailGroupEvent()
}
