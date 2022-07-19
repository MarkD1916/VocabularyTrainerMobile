package com.example.vocabularytrainer.auth.use_case

import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.domain.auth.use_case.ValidateConfirmPassword
import com.example.vocabularytrainer.domain.auth.use_case.ValidationResult
import com.google.common.truth.Truth
import com.vmakd1916gmail.com.core.util.UiText
import org.junit.Before
import org.junit.Test

class ValidateConfirmPasswordTest {

    private lateinit var validateConfirmPassword: ValidateConfirmPassword
    private lateinit var validationResultSuccess: ValidationResult
    private lateinit var validationResultErrorDontMatch: ValidationResult
    private lateinit var validationResultErrorBlank: ValidationResult

    private lateinit var password: String
    private lateinit var password_dont_match: String
    private lateinit var confirm_password: String
    private lateinit var confirm_password_blank: String

    @Before
    fun setUp() {
        password = "qwerty"
        password_dont_match = "123"
        confirm_password = "qwerty"
        confirm_password_blank = ""


        validateConfirmPassword = ValidateConfirmPassword()
        validationResultSuccess = ValidationResult(success = true, error = null)
        validationResultErrorDontMatch = ValidationResult(
            success = false, error = UiText.StringResource(
                R.string.error_password_dont_match
            )
        )
        validationResultErrorBlank = ValidationResult(
            success = false, error = UiText.StringResource(
                R.string.error_blank_password
            )
        )
    }

    @Test
    fun `Validate confirm password normal`() {
        val result = validateConfirmPassword.execute(password = password, confirmPassword = confirm_password)
        Truth.assertThat(result).isEqualTo(validationResultSuccess)
    }

    @Test
    fun `Validate confirm password don't match error`() {
        val result = validateConfirmPassword.execute(password = password_dont_match, confirmPassword = confirm_password)
        Truth.assertThat(result).isEqualTo(validationResultErrorDontMatch)
    }

    @Test
    fun `Validate password don't match error`() {
        val result = validateConfirmPassword.execute(password = confirm_password, confirmPassword = password_dont_match)
        Truth.assertThat(result).isEqualTo(validationResultErrorDontMatch)
    }

    @Test
    fun `Validate confirm password blank error`() {
        val result = validateConfirmPassword.execute(password = password, confirmPassword = confirm_password_blank)
        Truth.assertThat(result).isEqualTo(validationResultErrorBlank)
    }
}