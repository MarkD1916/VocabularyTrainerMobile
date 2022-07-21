package com.example.vocabularytrainer.auth.use_case

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.domain.auth.use_case.ValidateEmail
import com.example.vocabularytrainer.domain.auth.use_case.ValidationResult
import com.vmakd1916gmail.com.core.util.UiText
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ValidateEmailAndroidTest {
    private lateinit var validateEmail: ValidateEmail
    private lateinit var validationResultErrorNotValid: ValidationResult

    private lateinit var email_not_valid: String

    @Before
    fun setUp() {
        email_not_valid = "123"
        validateEmail = ValidateEmail()
        validationResultErrorNotValid = ValidationResult(
            success = false, error = UiText.StringResource(
                R.string.error_not_valid_email
            )
        )
    }

    @Test
    fun email_is_not_valid() {
        val result = validateEmail.execute(email_not_valid)
        Assert.assertEquals(result, validationResultErrorNotValid)
    }

}