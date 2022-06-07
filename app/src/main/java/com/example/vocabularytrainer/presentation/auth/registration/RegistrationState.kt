package com.example.vocabularytrainer.presentation.auth.registration

sealed class AuthResponseResult() {
    object Loading: AuthResponseResult()
    object Success: AuthResponseResult()
    data class Error(val message:String): AuthResponseResult()
}


data class RegistrationState(
    val email: String = "mark@gmail.com",
    val emailError: String? = null,
    val password: String = "1234567890",
    val passwordError: String? = null,
    val passwordRequire: String? = "",
    val confirmPassword: String = "1234567890",
    val confirmPasswordError: String? = null,
    val confirmPasswordRequire: String? = "",

    val authResponseResult: AuthResponseResult? = null
) {
    fun getReadOnlyValue():Boolean {
        return authResponseResult is AuthResponseResult.Loading || authResponseResult is AuthResponseResult.Success
    }
}