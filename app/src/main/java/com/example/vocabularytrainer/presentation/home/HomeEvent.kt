package com.example.vocabularytrainer.presentation.home

import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse


sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    class Loading<T>(data: T? = null) : Resource<T>(data)

    class NoAction<T>(data: T? = null)  : Resource<T>(data)

}

sealed class HomeEvent {
    object GetAllGroup: HomeEvent()

    data class Action1(val index: Int): HomeEvent()
    object Action2: HomeEvent()

}


