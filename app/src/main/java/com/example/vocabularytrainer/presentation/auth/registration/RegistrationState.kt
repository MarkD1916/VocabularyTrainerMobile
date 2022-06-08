package com.example.vocabularytrainer.presentation.auth.registration

sealed class AuthResponseResult() {
    object Loading: AuthResponseResult()
    object Success: AuthResponseResult()
    data class Error(val message:String): AuthResponseResult()
}


data class RegistrationState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordRequire: String? = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val confirmPasswordRequire: String? = "",

    val authResponseResult: AuthResponseResult? = null
) {
    fun getReadOnlyValue():Boolean {
        return authResponseResult is AuthResponseResult.Loading || authResponseResult is AuthResponseResult.Success
    }
}