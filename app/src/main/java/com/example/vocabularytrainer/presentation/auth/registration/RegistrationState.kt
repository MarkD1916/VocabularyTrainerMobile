package com.example.vocabularytrainer.presentation.auth.registration

data class RegistrationState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = "",
    val passwordRequire: String? = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String? = "",
    val confirmPasswordRequire: String? = ""
) {
}