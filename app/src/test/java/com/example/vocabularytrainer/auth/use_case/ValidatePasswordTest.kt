package com.example.vocabularytrainer.auth.use_case

import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.domain.auth.use_case.ValidatePassword
import com.example.vocabularytrainer.domain.auth.use_case.ValidationResult
import com.vmakd1916gmail.com.core.util.UiText
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidatePasswordTest {
    private lateinit var validatePassword: ValidatePassword
    private lateinit var validationResultSuccess: ValidationResult
    private lateinit var validationResultErrorShort: ValidationResult
    private lateinit var validationResultErrorBlank: ValidationResult

    private lateinit var password_normal: String
    private lateinit var password_blank: String
    private lateinit var password_short: String

    @Before
    fun setUp() {
        password_normal = "123456789"
        password_blank = ""
        password_short = "123"
        validatePassword = ValidatePassword()
        validationResultSuccess = ValidationResult(success = true, error = null)
        validationResultErrorShort = ValidationResult(
            success = false, error = UiText.StringResource(
                R.string.error_short_password
            )
        )
        validationResultErrorBlank = ValidationResult(
            success = false, error = UiText.StringResource(
                R.string.error_blank_password
            )
        )
    }

    @Test
    fun `Validate password normal`() {
        val result = validatePassword.execute(password_normal)
        assertThat(result).isEqualTo(validationResultSuccess)
    }

    @Test
    fun `Validate password short error`() {
        val result = validatePassword.execute(password_short)
        assertThat(result).isEqualTo(validationResultErrorShort)
    }

    @Test
    fun `Validate password blank error`() {
        val result = validatePassword.execute(password_blank)
        assertThat(result).isEqualTo(validationResultErrorBlank)
    }
}