package com.example.vocabularytrainer.domain.auth.use_case

import com.example.vocabularytrainer.R
import com.vmakd1916gmail.com.core.util.UiText


class ValidateConfirmPassword {

    fun execute(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isBlank()) {
            return ValidationResult(
                success = false,
                error = UiText.StringResource(R.string.error_blank_confirm_password)
            )
        }

        if (confirmPassword != password) {
            return ValidationResult(
                success = false,
                error = UiText.StringResource(R.string.error_password_dont_match)
            )
        }
        return ValidationResult(success = true)
    }
}