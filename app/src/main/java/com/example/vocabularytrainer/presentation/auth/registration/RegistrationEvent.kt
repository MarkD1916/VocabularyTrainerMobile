package com.example.vocabularytrainer.presentation.auth.registration

import com.example.vocabularytrainer.data.remote.auth.response.RegisterResponse
import com.example.vocabularytrainer.presentation.auth.AuthEvent


sealed class RegistrationEvent: AuthEvent {
    data class OnConfirmPasswordEnter(
        val confirmPassword: String = ""
    ) : RegistrationEvent()

    object Submit : RegistrationEvent()

    data class Success(
        val result: RegisterResponse? = null
    ) : RegistrationEvent()

    data class Error(val message: String) : RegistrationEvent()

    data class OnCountryFlagSelected(val countryFlagUrl: String = "") : RegistrationEvent()

    data class OnFirstNameEnter(val firstName: String = "") : RegistrationEvent()

    data class OnLastNameEnter(val lastName: String = "") : RegistrationEvent()

    data class OnBioEnter(val bio: String = "") : RegistrationEvent()
}