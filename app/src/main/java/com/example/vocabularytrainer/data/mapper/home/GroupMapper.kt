package com.example.vocabularytrainer.data.mapper.home

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.remote.home.remote.request.GroupRequest
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.presentation.home.Resource

@RequiresApi(Build.VERSION_CODES.O)
fun GroupEntity.toGroup(): Group {
    return Group(
        id = id.toString(),
        name = name,
        isSync = isSync,
        state = Resource.NoAction(null)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun GroupEntity.toGroupSuccess(): Group {
    return Group(
        id = id,
        name = name,
        isSync = isSync,
        state = Resource.Success(null)
    )
}

fun GroupEntity.toGroupRequest(userId: String): GroupRequest {
    return GroupRequest(
        id = id,
        groupName = name,
        date = System.currentTimeMillis(),
        owners = listOf(userId)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Group.toGroupEntity() : GroupEntity {
    return GroupEntity(
        id = id,
        name=name,
        isSync = isSync
    )
}

fun Group.toGroupRequest(userId: String) : GroupRequest {
    return GroupRequest(
        id = id,
        groupName=name,
        date = System.currentTimeMillis(),
        owners = listOf(userId)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun GroupResponse.toGroupEntity(): GroupEntity {
    return GroupEntity(
        id = id,
        name = name,
        isSync = true
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun GroupRequest.toGroupEntity(): GroupEntity {
    return GroupEntity(
        id = id,
        name = groupName,
        isSync = false,
    )
}