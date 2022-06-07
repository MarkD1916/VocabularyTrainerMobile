package com.example.vocabularytrainer.presentation.auth.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ValidationEvent() {
    object Success : ValidationEvent()
}


@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val authUseCases: AuthUseCases
) : ViewModel() {

    var state by mutableStateOf(RegistrationState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

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
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }


}