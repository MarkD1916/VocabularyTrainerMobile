package com.example.vocabularytrainer.presentation.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.preferences.AuthPreferenceImpl
import com.example.vocabularytrainer.data.preferences.HomePreferenceImpl
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult
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
    private val authPreference: AuthPreferenceImpl,
    private val homePreferences: HomePreferenceImpl
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
                onEvent(AuthEvent.Loading)
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
        if (authPreference.getStoredToken().isNotBlank()) {
            viewModelScope.launch {
                _uiEvent.send(
                    UiEvent.Navigate(
                        route = Route.HOME
                    )
                )
            }
        }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Loading -> {
                state = state.copy(loginResponseResult = AuthResponseResult.Loading)
            }
            is LoginEvent.SuccessLogin -> {
                authPreference.setStoredToken(event.result?.token ?: "")
                homePreferences.setAllGroupId(event.result?.mainGroupId ?:"")
                state = state.copy(loginResponseResult = AuthResponseResult.Success)
            }

            is LoginEvent.Error -> {
                state = state.copy(loginResponseResult = AuthResponseResult.Error(event.message))
            }

            is LoginEvent.LogOut -> {
                logOutUser()
            }
            is LoginEvent.Login -> {
                submitData()
            }
            is AuthEvent.OnEmailEnter -> {
                state = state.copy(email = event.email)
            }
            is AuthEvent.OnPasswordEnter -> {
                state = state.copy(password = event.password)
            }

            is LoginEvent.SetUserId -> {

                viewModelScope.launch {
                    authPreference.setUserId(event.result?.id.toString())
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.HOME
                        )
                    )
                }
            }

        }
    }

    private fun submitData() {
        val emailResult = authUseCases.validateEmail.execute(state.email)
        val passwordResult = authUseCases.validatePassword.execute(state.password)
        val hasError = listOf(
            emailResult,
            passwordResult
        ).any {
            !it.success
        }
        if (hasError) {
            state = state.copy(
                emailError = emailResult.error,
                passwordError = passwordResult.error
            )
            return
        } else {
            state = state.copy(
                emailError = null,
                passwordError = null
            )
        }
        loginUser()
    }

    private fun loginUser() {
        onEvent(AuthEvent.Loading)
        viewModelScope.launch {
            val job1 = launch {
                onEvent(authUseCases.loginUser.execute(state.password, state.email))
            }
            job1.join()
            onEvent(authUseCases.getCurrentUser.execute())
        }
    }

    private fun logOutUser() {
        viewModelScope.launch {
            onEvent(authUseCases.logoutUser.execute())
            authPreference.setStoredToken("")
            authPreference.setStoredEmail("")
            authPreference.setStoredPassword("")
        }
    }

}