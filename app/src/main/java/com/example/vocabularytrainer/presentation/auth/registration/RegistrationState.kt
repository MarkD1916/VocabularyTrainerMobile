package com.example.vocabularytrainer.presentation.auth.registration

data class RegistrationState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordRequire: String? = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val confirmPasswordRequire: String? = ""
) {
}