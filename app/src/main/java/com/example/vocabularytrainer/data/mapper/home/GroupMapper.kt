package com.example.vocabularytrainer.data.mapper.home

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.remote.home.remote.request.GroupRequest
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.home.Resource
import java.time.LocalDateTime
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
fun GroupEntity.toGroup(): Group {
    return Group(
        id = id.toString(),
        name = name,
        isSync = isSync,
        state = Resource.NoAction(null),
        date = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun GroupEntity.toGroupSuccess(): Group {
    return Group(
        id = id.toString(),
        name = name,
        isSync = isSync,
        state = Resource.Success(null),
        date = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    )
}

fun GroupEntity.toGroupRequest(user: String): GroupRequest {
    return GroupRequest(
        group_name = name,
        user = user,
        id = id
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Group.toGroupEntity() : GroupEntity {
    return GroupEntity(
        id = id,
        name=name,
        isSync = isSync,
        date = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    )
}

fun Group.toGroupRequest(user: String) : GroupRequest {
    return GroupRequest(
        group_name=name,
        user = user,
        id = id
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun GroupResponse.toGroupEntity(): GroupEntity {
    return GroupEntity(
        id = id,
        name = name,
        isSync = true,
        date = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun GroupRequest.toGroupEntity(): GroupEntity {
    return GroupEntity(
        name = group_name,
        isSync = false,
        id = id,
        date = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
    )
}