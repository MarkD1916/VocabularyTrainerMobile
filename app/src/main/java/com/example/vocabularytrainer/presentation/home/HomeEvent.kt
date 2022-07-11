package com.example.vocabularytrainer.presentation.home

import com.androiddevs.ktornoteapp.data.remote.interceptors.Variables
import com.example.vocabularytrainer.domain.home.model.Group


sealed class Resource<T>(open val data: T? = null, val message: String? = null) {

    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    open class Loading<T>(data: T? = null) : Resource<T>(data)

    class NoAction<T>(data: T? = null) : Resource<T>(data)

}

sealed class LoadingType<T>(override val data: T? = null) : Resource.Loading<T>() {

    class FullScreenLoading<T>(data: T? = null) : LoadingType<T>(data)

    class ElementLoading<T>(data: T? = null) : LoadingType<T>(data)

    class LoadingFromDB<T>(data: T? = null) : LoadingType<T>(data)

    class FabLoading<T>(data: T? = null) : LoadingType<T>(data)
}

sealed class HomeEvent {
    object GetAllGroup : HomeEvent() {
        var loadingType: LoadingType<List<Group>> = LoadingType.LoadingFromDB()
    }

    data class DeleteGroup(
        val id: String
    ) : HomeEvent() {
        var loadingType: LoadingType<Group> = if (!Variables.isNetworkConnected) LoadingType.LoadingFromDB() else  LoadingType.ElementLoading()
    }

    data class Action1(val index: Int) : HomeEvent()
    object FabClick : HomeEvent()

    data class OnNewGroupNameEnter(val newGroupName: String) : HomeEvent()

    data class PostNewGroup(val group: Group) : HomeEvent() {
        var loadingType: LoadingType<Unit> = LoadingType.FabLoading()
    }

    data class OnToggleGroupClick(val groupId: String) : HomeEvent()

    data class OnDetailGroupClick(val groupId: String) : HomeEvent()

    object ShowFullScreenLoading : HomeEvent()

}


