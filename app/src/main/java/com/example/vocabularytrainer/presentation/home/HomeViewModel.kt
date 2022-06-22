package com.example.vocabularytrainer.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.ktornoteapp.data.remote.interceptors.Variables
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.mapper.home.toGroup
import com.example.vocabularytrainer.data.mapper.home.toGroupSuccess
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.domain.home.use_case.GetAllGroup
import com.example.vocabularytrainer.domain.home.use_case.HomeUseCases
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.vmakd1916gmail.com.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authPreference: AuthPreference,
    private val authUseCases: AuthUseCases,
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    var state by mutableStateOf(HomeState())
    private val _uiEvent = Channel<UiEvent>()
    var uiEvent: Flow<UiEvent>? = _uiEvent.receiveAsFlow()

    private val _openAddGroupDialog = Channel<UiEvent>()
    var openAddGroupDialog: Flow<UiEvent>? = _openAddGroupDialog.receiveAsFlow()

    private var getAllGroup: Job? = null

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
                    }.launchIn(viewModelScope)
            }

            is HomeEvent.DeleteGroup -> {
                state = state.copy(
                    group = state.group.map {
                        if (it.id == event.id) {
                            it.copy(state = event.loadingType)
                        } else it
                    }
                )
                viewModelScope.launch {
                    homeUseCases.deleteGroup.execute(event.id)
                }

//                doAction1(event.index)

            }

            is HomeEvent.FabClick -> {

//                viewModelScope.launch {
//                    _openAddGroupDialog.send(
//                        UiEvent.OpenAddGroupDialog(true)
//                    )
//                }
            }

            is HomeEvent.OnNewGroupNameEnter -> {
                state = state.copy(newGroupName = event.newGroupName)
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

        viewModelScope.launch {
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