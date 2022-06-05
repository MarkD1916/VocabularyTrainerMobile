package com.example.vocabularytrainer.domain.auth.use_case

import android.util.Patterns

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                success = false,
                error = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                success = false,
                error = "That's not a valid email"
            )
        }
        return ValidationResult(success = true)
    }
}