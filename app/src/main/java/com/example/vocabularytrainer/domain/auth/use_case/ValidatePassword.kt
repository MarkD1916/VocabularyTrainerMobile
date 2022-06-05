package com.example.vocabularytrainer.domain.auth.use_case

import com.example.vocabularytrainer.util.Constants.PASSWORD_LENGTH

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                success = false,
                error = "The password can't be blank"
            )
        }

        if (password.length < PASSWORD_LENGTH) {
            return ValidationResult(
                success = false,
                error = "Your password must contain at least 9 characters"
            )
        }
        return ValidationResult(success = true)
    }
}