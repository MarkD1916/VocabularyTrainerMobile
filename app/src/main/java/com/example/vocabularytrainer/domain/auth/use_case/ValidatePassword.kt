package com.example.vocabularytrainer.domain.auth.use_case

import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.util.Constants.PASSWORD_LENGTH
import com.vmakd1916gmail.com.core.util.UiText

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                success = false,
                error = UiText.StringResource(R.string.error_blank_password)
            )
        }

        if (password.length < PASSWORD_LENGTH) {
            return ValidationResult(
                success = false,
                error = UiText.StringResource(R.string.error_short_password)
            )
        }
        return ValidationResult(success = true)
    }
}