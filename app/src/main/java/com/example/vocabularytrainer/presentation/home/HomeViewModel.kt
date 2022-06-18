package com.example.vocabularytrainer.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.domain.home.use_case.HomeUseCases
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.vmakd1916gmail.com.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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

    private val _isRefreshing = MutableStateFlow(false)

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
                    group = event.loadingType
                )
                viewModelScope.launch {
                    homeUseCases.getAllGroup.execute()
                        .retry(retries = 3) { cause ->
                            return@retry cause is TimeoutException
                        }
                        .collect {
                            state = state.copy(
                                group = it
                            )
                        }
                }
            }

            is HomeEvent.Action1 -> {

                state = state.copy(
                    actionState_1 = state.actionState_1.map {
                        if (it.id == event.index) {
                            it.copy(state = Resource.Loading())
                        } else it
                    }
                )
//                doAction1(event.index)

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
        HomeEvent.GetAllGroup.loadingType = LoadingType.ElementLoading()
        onHomeEvent(HomeEvent.GetAllGroup)
    }

    fun onErrorEvent() {

    }


    private fun logOutUser() {
        viewModelScope.launch {
            authPreference.setStoredToken("")
            authPreference.setStoredEmail("")
            onEvent(authUseCases.logoutUser.execute())
            authPreference.setStoredPassword("")
        }
    }

    fun getToken(): String {
        return authPreference.getStoredToken()
    }
}