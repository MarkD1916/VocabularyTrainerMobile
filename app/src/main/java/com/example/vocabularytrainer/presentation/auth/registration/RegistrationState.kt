package com.example.vocabularytrainer.presentation.auth.registration

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

sealed class AuthResponseResult {
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

    val registerResponseResult: AuthResponseResult? = null,

    val firstName: String = "",
    val lastName: String = "",
    val bio: String = "",
    val countryUrl: String = ""
) {
    fun getReadOnlyValue():Boolean {
        return registerResponseResult is AuthResponseResult.Loading || registerResponseResult is AuthResponseResult.Success
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCreatedDate(): LocalDate {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val h = calendar[Calendar.HOUR]
        val m = calendar[Calendar.MINUTE]
        return LocalDate.now()
    }

}