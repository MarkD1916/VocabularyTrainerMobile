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
                //state = state.copy(***=Loading)
                getGroupFromServer()
            }

            is HomeEvent.Action1 -> {
                state = state.copy(actionState_1 = Resource.Loading())
                doAction1()
            }

            is HomeEvent.Action2 -> {
                state = state.copy(actionState_2 = Resource.Loading())
                doAction2()
            }
        }
    }

    private fun doAction1() {
        viewModelScope.launch {
            delay(5000)
            state = state.copy(actionState_1 = Resource.Success("1 Success"))
        }
    }

    private fun doAction2() {
        viewModelScope.launch {
            delay(10000)
            state = state.copy(actionState_2 = Resource.Success("2 Success"))
        }
    }




    private fun getGroupFromServer() {
        viewModelScope.launch {
            when(val result = homeUseCases.getAllGroup.execute()){
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
            onEvent(authUseCases.logoutUser.execute())
            authPreference.setStoredToken("")
            authPreference.setStoredEmail("")
            authPreference.setStoredPassword("")
        }
    }

    fun getToken(): String {
        return authPreference.getStoredToken()
    }
}