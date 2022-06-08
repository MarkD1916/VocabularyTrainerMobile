package com.example.vocabularytrainer.data.preferences

import android.content.SharedPreferences
import com.example.vocabularytrainer.domain.preferences.Preferences
import com.example.vocabularytrainer.util.Constants.KEY_LOGGED_IN_EMAIL
import com.example.vocabularytrainer.util.Constants.KEY_PASSWORD

class AuthPreference(
    private val prefs: SharedPreferences
) : Preferences {

    override fun getStoredEmail(): String {
        return prefs.getString(KEY_LOGGED_IN_EMAIL, "")!!
    }

    override fun setStoredEmail(email: String) {
        prefs
            .edit()
            .putString(KEY_LOGGED_IN_EMAIL, email)
            .apply()
    }

    override fun getStoredPassword(): String {
        return prefs.getString(KEY_PASSWORD, "")!!
    }

    override fun setStoredPassword(password: String) {
        prefs
            .edit()
            .putString(KEY_PASSWORD, password)
            .apply()
    }
}