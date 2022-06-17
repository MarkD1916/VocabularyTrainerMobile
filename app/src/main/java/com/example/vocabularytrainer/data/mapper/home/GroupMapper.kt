package com.example.vocabularytrainer.data.mapper.home

import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group

fun GroupEntity.toGroup(): Group {
    return Group(
        id = id,
        name = name,
        isSync = isSync
    )
}

fun Group.toGroupEntity() : GroupEntity {
    return GroupEntity(
        id=id,
        name=name,
        isSync = isSync,
    )
}

fun GroupResponse.toGroupEntity(): GroupEntity {
    return GroupEntity(
        id = id,
        name = name,
        isSync = true
    )
}