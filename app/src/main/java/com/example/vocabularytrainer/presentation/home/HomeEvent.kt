package com.example.vocabularytrainer.presentation.home

import com.example.vocabularytrainer.data.remote.home.response.GroupResponse


sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    class Loading<T>(data: T? = null) : Resource<T>(data)

    class NoAction<T>(data: T? = null)  : Resource<T>(data)

}

sealed class HomeEvent {
    data class GetAllGroup(val data: GroupResponse): HomeEvent()

    object Action1: HomeEvent()
    object Action2: HomeEvent()

}


