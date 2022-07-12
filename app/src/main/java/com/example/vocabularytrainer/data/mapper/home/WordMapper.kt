package com.example.vocabularytrainer.data.mapper.home

import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.remote.detail_group.remote.response.WordResponse
import com.example.vocabularytrainer.domain.detail_group.model.Word
import com.example.vocabularytrainer.presentation.home.Resource

fun WordResponse.toWordEntity(): WordEntity {
    return WordEntity(
        groupId = groupId,
        word = word,
        translate = translate,
        transcription = transcription,
        group_name = group_name,
        isSync = true,
        id = id
    )
}

fun WordEntity.toWord(): Word {
    return Word(
        word = word,
        translate = translate,
        transcription = transcription,
        group_name = group_name,
        isSync = isSync,
        id = id,
        wordState = Resource.Success(null)
    )
}
