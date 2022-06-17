package com.example.vocabularytrainer.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.data.remote.home.local.Item
import com.example.vocabularytrainer.data.remote.home.local.Test
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.domain.home.use_case.HomeUseCases
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.vmakd1916gmail.com.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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

    init {

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
                    group = Resource.Loading()
                )
                viewModelScope.launch {
                    state = state.copy(
                        group = homeUseCases.getAllGroup.execute()
                    )

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


    private fun getGroupFromServer() {
        viewModelScope.launch {
            when (val result = homeUseCases.getAllGroup.execute()) {
                is Resource.Success -> {
                    result.data//state = state.copy
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    result.message//state = state.copy
                }
            }
        }
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