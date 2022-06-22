package com.example.vocabularytrainer.domain.auth.use_case

data class AuthUseCases(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateConfirmPassword: ValidateConfirmPassword,
    val registerUser: RegisterUser,
    val loginUser: LoginUser,
    val logoutUser: LogoutUser,
    val getCurrentUser: GetCurrentUser
)
