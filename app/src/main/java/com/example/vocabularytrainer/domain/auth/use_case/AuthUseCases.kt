package com.example.vocabularytrainer.domain.auth.use_case

data class AuthUseCases(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateConfirmPassword: ValidateConfirmPassword,
    val registerUser: RegisterUser
)
