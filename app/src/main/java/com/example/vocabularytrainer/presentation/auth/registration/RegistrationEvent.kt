package com.example.vocabularytrainer.presentation.auth.registration

sealed class RegistrationEvent {
    data class OnEmailEnter(
        val email: String = ""
    ): RegistrationEvent()

    data class OnPasswordEnter(
        val password: String = ""
    ): RegistrationEvent()

    data class OnConfirmPasswordEnter(
        val confirmPassword: String = ""
    ): RegistrationEvent()
}