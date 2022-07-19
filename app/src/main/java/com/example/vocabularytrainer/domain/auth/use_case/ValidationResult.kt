package com.example.vocabularytrainer.domain.auth.use_case

import com.vmakd1916gmail.com.core.util.UiText

data class ValidationResult(
    val success: Boolean,
    val error: UiText.StringResource? = null
)
