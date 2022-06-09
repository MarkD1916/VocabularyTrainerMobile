package com.example.vocabularytrainer.presentation.auth.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ValidationEvent {
    object Success : ValidationEvent()
    data class Error(val message:String): ValidationEvent()
}


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val authPreference: AuthPreference
) : ViewModel() {

    var state by mutableStateOf(RegistrationState())
    var job: Job? by mutableStateOf(null)
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        if(authPreference.getStoredEmail().isNotBlank()
            && authPreference.getStoredPassword().isNotBlank()
        ){
            state =  state.copy(
                email = authPreference.getStoredEmail(),
                password = authPreference.getStoredPassword(),
                confirmPassword = authPreference.getStoredPassword(),
                authResponseResult = AuthResponseResult.Success
            )
        }
    }

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnEmailEnter -> {
                state = state.copy(email = event.email)
            }
            is RegistrationEvent.OnPasswordEnter -> {
                state = state.copy(password = event.password)
            }
            is RegistrationEvent.OnConfirmPasswordEnter -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }
            is RegistrationEvent.Submit -> {
                submitData()
            }
            is RegistrationEvent.Loading -> {
                state = state.copy(authResponseResult = AuthResponseResult.Loading)
            }
            is RegistrationEvent.Success -> {
                state = state.copy(authResponseResult = AuthResponseResult.Success)
                authPreference.setStoredEmail(state.email)
                authPreference.setStoredPassword(state.password)
            }
            is RegistrationEvent.Error -> {
                state = state.copy(authResponseResult = AuthResponseResult.Error(event.message))
            }
        }
    }

    private fun submitData() {
        val emailResult = authUseCases.validateEmail.execute(state.email)
        val passwordResult = authUseCases.validatePassword.execute(state.password)
        val confirmPasswordResult =
            authUseCases.validateConfirmPassword.execute(state.password, state.confirmPassword)
        val hasError = listOf(
            emailResult,
            passwordResult,
            confirmPasswordResult
        ).any {
            !it.success
        }
        if (hasError) {
            state = state.copy(
                emailError = emailResult.error,
                passwordError = passwordResult.error,
                confirmPasswordError = confirmPasswordResult.error
            )
            return
        }
        else {
            state = state.copy(
                emailError = null,
                passwordError = null,
                confirmPasswordError = null
            )
        }
        getDataFromServer()
    }

    private fun getDataFromServer() {
        viewModelScope.launch {
            onEvent(RegistrationEvent.Loading)
            onEvent(authUseCases.registerUser.execute(state.password, state.email))

        }
    }


}