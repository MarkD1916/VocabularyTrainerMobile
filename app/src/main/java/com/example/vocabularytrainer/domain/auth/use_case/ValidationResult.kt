package com.example.vocabularytrainer.domain.auth.use_case

data class ValidationResult(
    val success: Boolean,
    val error: String? = null
)
