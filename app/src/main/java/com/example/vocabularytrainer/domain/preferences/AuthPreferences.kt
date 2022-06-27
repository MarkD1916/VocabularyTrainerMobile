package com.example.vocabularytrainer.domain.preferences

interface AuthPreferences {
    fun getStoredEmail(): String

    fun setStoredEmail(email: String)

    fun getStoredPassword(): String

    fun setStoredPassword(password: String)

    fun getStoredToken(): String

    fun setStoredToken(token: String)

    fun getUserId(): String

    fun setUserId(id: String)
}