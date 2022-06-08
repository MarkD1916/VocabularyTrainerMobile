package com.example.vocabularytrainer.domain.preferences

interface Preferences {
    fun getStoredEmail(): String

    fun setStoredEmail(email: String)

    fun getStoredPassword(): String

    fun setStoredPassword(password: String)
}