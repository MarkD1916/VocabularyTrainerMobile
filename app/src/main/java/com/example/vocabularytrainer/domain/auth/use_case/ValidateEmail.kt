package com.example.vocabularytrainer.domain.auth.use_case

import android.util.Patterns
import com.example.vocabularytrainer.R
import com.vmakd1916gmail.com.core.util.UiText

class ValidateEmail {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                success = false,
                error = UiText.StringResource(R.string.error_blank_email)
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                success = false,
                error = UiText.StringResource(R.string.error_not_valid_email)
            )
        }
        return ValidationResult(success = true)
    }
}