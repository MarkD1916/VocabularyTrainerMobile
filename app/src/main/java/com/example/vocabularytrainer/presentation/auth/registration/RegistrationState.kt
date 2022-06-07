package com.example.vocabularytrainer.presentation.auth.registration

data class RegistrationState(
    val email: String = "mark@gmail.com",
    val emailError: String? = null,
    val password: String = "1234567890",
    val passwordError: String? = null,
    val passwordRequire: String? = "",
    val confirmPassword: String = "1234567890",
    val confirmPasswordError: String? = null,
    val confirmPasswordRequire: String? = "",

    val isLoading: Boolean = false
) {
}