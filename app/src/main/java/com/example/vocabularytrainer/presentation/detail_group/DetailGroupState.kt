package com.example.vocabularytrainer.presentation.detail_group

import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.presentation.home.Resource

data class DetailGroupState(
    var words: List<WordEntity> = listOf(),
    val newWordNative: String = "",
    val newWordTranslate: String = "",
    var screenState: Resource<*>? = null,
    var groupId:String = ""
)
