package com.example.vocabularytrainer.presentation.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.example.vocabularytrainer.navigation.Route
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

@RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
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
                    .map { it ->
                        val data = it.data
                        Log.d("LOL1", "onHomeEvent: ${data?.size}")
                        data?.map {
                            it.toGroupSuccess()
                        }
                    }
                    .flowOn(Dispatchers.IO)
                    .onEach { groupList ->
                        Log.d("LOL1", "onHomeEvent: ${groupList?.size}")
                        state = state.copy(
                            group = groupList ?: listOf(),
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

            is HomeEvent.OnToggleGroupClick -> {
                state = state.copy(
                    group = state.group.map {
                        if (it.id == event.groupId) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }

            is HomeEvent.OnDetailGroupClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            Route.DETAIL_GROUP + "/${event.groupId}"
                        )
                    )
                }

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun refresh() {
        if (!Variables.isNetworkConnected) {
            HomeEvent.GetAllGroup.loadingType = LoadingType.LoadingFromDB(state.group)
        } else {
            HomeEvent.GetAllGroup.loadingType = LoadingType.ElementLoading(state.group)
        }
        onHomeEvent(HomeEvent.GetAllGroup)
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

    override fun onCleared() {
        super.onCleared()
    }
}