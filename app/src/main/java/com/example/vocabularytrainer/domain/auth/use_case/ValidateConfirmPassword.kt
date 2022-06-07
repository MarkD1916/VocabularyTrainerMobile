package com.example.vocabularytrainer.domain.auth.use_case


class ValidateConfirmPassword {

    fun execute(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isBlank()) {
            return ValidationResult(
                success = false,
                error = "The confirm password can't be blank"
            )
        }

        if (confirmPassword != password) {
            return ValidationResult(
                success = false,
                error = "The passwords don't match"
            )
        }
        return ValidationResult(success = true)
    }
}