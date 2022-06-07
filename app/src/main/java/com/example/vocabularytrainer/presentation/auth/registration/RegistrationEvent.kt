package com.example.vocabularytrainer.presentation.auth.registration

sealed class RegistrationEvent {
    data class OnEmailEnter(
        val email: String = ""
    ) : RegistrationEvent()

    data class OnPasswordEnter(
        val password: String = ""
    ) : RegistrationEvent()

    data class OnConfirmPasswordEnter(
        val confirmPassword: String = ""
    ) : RegistrationEvent()

    object Submit : RegistrationEvent()

    object Loading: RegistrationEvent()
    object Success: RegistrationEvent()
    data class Error(val message:String): RegistrationEvent()
}