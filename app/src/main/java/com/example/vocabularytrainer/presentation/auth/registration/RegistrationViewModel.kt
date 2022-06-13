package com.example.vocabularytrainer.presentation.auth.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.presentation.auth.getCountryList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
                registerResponseResult = AuthResponseResult.Success
            )
        }
    }

    val countries = getCountryList()
    var searchRequest = mutableStateOf("")
    val searchResult = mutableStateOf(getCountryList())


    fun collectCountry() {
        viewModelScope.launch {
            searchResult.value =countries.filter {country->
                searchRequest.value.length>= 3
            }.filter {
                it.name.startsWith(searchRequest.value.replaceFirstChar { it.uppercase() })
            }


            }
    }

    fun onEvent(event: GeneralEvent) {
        when (event) {
            is GeneralEvent.Loading -> {
                state = state.copy(registerResponseResult = AuthResponseResult.Loading)
            }

            is GeneralEvent.NoInternetConnection -> {

            }


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

            is RegistrationEvent.Success -> {
                state = state.copy(registerResponseResult = AuthResponseResult.Success)
                authPreference.setStoredEmail(state.email)
                authPreference.setStoredPassword(state.password)
            }
            is RegistrationEvent.Error -> {
                state = state.copy(registerResponseResult = AuthResponseResult.Error(event.message))
            }
            is RegistrationEvent.OnCountryFlagSelected -> {
                state = state.copy(countryUrl = event.countryFlagUrl)
            }
            is RegistrationEvent.OnFirstNameEnter -> {
                state = state.copy(firstName = event.firstName)
            }
            is RegistrationEvent.OnLastNameEnter -> {
                state = state.copy(lastName = event.lastName)
            }
            is RegistrationEvent.OnBioEnter -> {
                state = state.copy(bio = event.bio)
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
        registerUser()
    }

    private fun registerUser() {
        viewModelScope.launch {
            onEvent(GeneralEvent.Loading)
            onEvent(authUseCases.registerUser.execute(state.password, state.email))
        }
    }


}