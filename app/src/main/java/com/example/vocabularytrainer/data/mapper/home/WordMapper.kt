package com.example.vocabularytrainer.data.mapper.home

import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.remote.home.remote.response.WordResponse

fun WordResponse.toWordEntity(): WordEntity {
    return WordEntity(
        groupId = groupId,
        word = word,
        translate = translate,
        group_name = group_name,
        isSync = true,
        id = id
    )
}

