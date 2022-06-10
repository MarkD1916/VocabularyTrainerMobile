package com.example.vocabularytrainer.presentation.auth.registration

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

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

    val authResponseResult: AuthResponseResult? = null,

    val firstName: String = "",
    val lastName: String = "",
    val country: String = "",
    val bio: String = ""
) {
    fun getReadOnlyValue():Boolean {
        return authResponseResult is AuthResponseResult.Loading || authResponseResult is AuthResponseResult.Success
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCreatedDate(): LocalDate {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        return LocalDate.of(year, month, day)
    }

}