package com.example.vocabularytrainer.presentation.detail_group

import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.home.LoadingType

sealed class DetailGroupEvent() {

    object OnNewWordClick : DetailGroupEvent()

    data class OnEditWordClick(
        val wordId: String
    ) : DetailGroupEvent()

    object GetAllWordsByGroup : DetailGroupEvent() {
        var loadingType: LoadingType<List<Group>> = LoadingType.FullScreenLoading()
    }

    data class OnToggleClick(val wordId: String) : DetailGroupEvent()
}
