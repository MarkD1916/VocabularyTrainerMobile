package com.example.vocabularytrainer.presentation.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult
import com.example.vocabularytrainer.presentation.auth.registration.GeneralEvent
import com.vmakd1916gmail.com.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val authPreference: AuthPreference
) : ViewModel() {
    var state by mutableStateOf(LoginState())
    private val _uiEvent = Channel<UiEvent>()
    var uiEvent: Flow<UiEvent>? = _uiEvent.receiveAsFlow()

    init {
        if (authPreference.getStoredEmail().isNotBlank()
            && authPreference.getStoredPassword().isNotBlank()
            && authPreference.getStoredToken().isBlank()
        ) {
            val email = authPreference.getStoredEmail()
            val password = authPreference.getStoredPassword()
            viewModelScope.launch {
                onEvent(GeneralEvent.Loading)
                onEvent(authUseCases.loginUser.execute(password, email))
                if (state.loginResponseResult is AuthResponseResult.Success) {

                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.HOME
                        )
                    )


                }
            }
        }
        if(authPreference.getStoredToken().isNotBlank()) {
            viewModelScope.launch {
                _uiEvent.send(
                    UiEvent.Navigate(
                        route = Route.HOME
                    )
                )
            }
        }
    }

    fun onEvent(event: GeneralEvent) {
        when (event) {
            is GeneralEvent.Loading -> {
                state = state.copy(loginResponseResult = AuthResponseResult.Loading)
            }
            is LoginEvent.Success -> {
                state = state.copy(loginResponseResult = AuthResponseResult.Success)
                authPreference.setStoredToken(event.result?.token?:"")
            }
            is LoginEvent.LogOut -> {
                authPreference.setStoredToken("")

            }
        }
    }
}