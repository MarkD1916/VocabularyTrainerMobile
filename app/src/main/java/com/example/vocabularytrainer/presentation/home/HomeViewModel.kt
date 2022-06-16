package com.example.vocabularytrainer.presentation.home

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