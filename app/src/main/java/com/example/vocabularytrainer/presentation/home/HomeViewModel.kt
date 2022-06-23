package com.example.vocabularytrainer.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.data.remote.interceptors.Variables
import com.example.vocabularytrainer.data.mapper.home.toGroupSuccess
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.domain.home.use_case.HomeUseCases
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.vmakd1916gmail.com.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import okhttp3.internal.threadName
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authPreference: AuthPreference,
    private val authUseCases: AuthUseCases,
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    var state by mutableStateOf(HomeState())
    private val _uiEvent = Channel<UiEvent>()
    var uiEvent: Flow<UiEvent>? = _uiEvent.receiveAsFlow()

    private var getAllGroup: Job? = null

    private var getAllNewGroup: Job? = null

    private val _isRefreshing = MutableStateFlow(false)

    val id by mutableStateOf(authPreference.getUserId())

    var isOpen by mutableStateOf(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        HomeEvent.GetAllGroup.loadingType = LoadingType.FullScreenLoading()
        onHomeEvent(HomeEvent.GetAllGroup)

    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is LoginEvent.LogOut -> {
                logOutUser()
            }
        }
    }

    fun onHomeEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetAllGroup -> {
                state = state.copy(
                    group = state.group.map {
                        it.copy(state = event.loadingType)
                    },
                    screenState = event.loadingType
                )
                getAllGroup?.cancel()

                getAllGroup = homeUseCases.getAllGroup.execute()
                    .onEach { groupList ->
                        val data = groupList.data
                        val mapData = data?.map {
                            it.toGroupSuccess()
                        }
                        state = state.copy(
                            group = mapData ?: listOf(),
                            screenState = null
                        )
                        Log.d("LOL", "onHomeEvent: ${coroutineContext.job}")
                    }.launchIn(viewModelScope.plus(Dispatchers.IO))
            }

            is HomeEvent.DeleteGroup -> {
                state = state.copy(
                    group = state.group.map {
                        if (it.id == event.id) {
                            it.copy(state = event.loadingType)
                        } else it
                    }
                )
                viewModelScope.launch(Dispatchers.IO) {
                    homeUseCases.deleteGroup.execute(event.id)
                }
            }

            is HomeEvent.FabClick -> {
            }

            is HomeEvent.OnNewGroupNameEnter -> {
                state = state.copy(newGroupName = event.newGroupName)
            }

            is HomeEvent.PostNewGroup -> {
                state = state.copy(fabState = event.loadingType)
                viewModelScope.launch(Dispatchers.IO) {
                    homeUseCases.addGroup.execute(event.group)
                    var list = state.group
                    list = list.plus(event.group)
                    state = state.copy(
                        group = list
                    )
                }


            }
        }
    }

//    private fun doAction1(index: Int) {
//        viewModelScope.launch {
//            delay(5000)
//            state = state.copy(
//                actionState_1 = state.actionState_1.map {
//                    if (it.id == index) {
//                        it.copy(state = Resource.Success("Success $index"))
//                    } else it
//                }
//            )
//        }
//    }


    fun refresh() {
        if (!Variables.isNetworkConnected) {
            HomeEvent.GetAllGroup.loadingType = LoadingType.LoadingFromDB(state.group)
        } else {
            HomeEvent.GetAllGroup.loadingType = LoadingType.ElementLoading(state.group)
        }
//        viewModelScope.launch {
//            _getAllGroupEvent.send(HomeEvent.GetAllGroup)
//        }
        onHomeEvent(HomeEvent.GetAllGroup)
    }

    fun onErrorEvent() {

    }

    private fun logOutUser() {

        viewModelScope.launch(Dispatchers.IO) {
            authPreference.setStoredToken("")
            authPreference.setStoredEmail("")
            authPreference.setUserId("")
            onEvent(authUseCases.logoutUser.execute())
            authPreference.setStoredPassword("")
        }

    }

    fun getToken(): String {
        return authPreference.getStoredToken()
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("LOL", "onCleared: ")
    }
}