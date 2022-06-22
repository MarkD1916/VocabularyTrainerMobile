package com.example.vocabularytrainer.presentation.home

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
        var loadingType: LoadingType<List<Group>> = LoadingType.FullScreenLoading()
    }

    data class DeleteGroup(
        val id: String
    ) : HomeEvent() {
        var loadingType: LoadingType<Group> = LoadingType.ElementLoading()
    }

    data class Action1(val index: Int) : HomeEvent()
    object FabClick : HomeEvent()

    data class OnNewGroupNameEnter(val newGroupName: String) : HomeEvent()

    data class AddNewGroup(val group: Group) : HomeEvent(){
        var loadingType: LoadingType<Unit> = LoadingType.FabLoading()
    }

}


