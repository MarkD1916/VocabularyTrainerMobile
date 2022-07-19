package com.example.vocabularytrainer.auth.use_case

import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.domain.auth.use_case.ValidateEmail
import com.example.vocabularytrainer.domain.auth.use_case.ValidationResult
import com.google.common.truth.Truth.assertThat
import com.vmakd1916gmail.com.core.util.UiText
import org.junit.Before
import org.junit.Test

class ValidateEmailTest {
    private lateinit var validateEmail: ValidateEmail
    private lateinit var validationResultSuccess: ValidationResult
    private lateinit var validationResultErrorBlank: ValidationResult

    private lateinit var email_blank: String

    @Before
    fun setUp2() {
        email_blank = ""
        validateEmail = ValidateEmail()
        validationResultSuccess = ValidationResult(success = true, error = null)
        validationResultErrorBlank = ValidationResult(
            success = false, error = UiText.StringResource(
                R.string.error_blank_email
            )
        )
    }

    @Test
    fun `Validate email blank error`() {
        val result = validateEmail.execute(email_blank)
        assertThat(result).isEqualTo(validationResultErrorBlank)
    }
}