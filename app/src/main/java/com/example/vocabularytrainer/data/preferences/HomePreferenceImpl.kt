package com.example.vocabularytrainer.data.preferences

import android.content.SharedPreferences
import com.example.vocabularytrainer.domain.preferences.HomePreferences
import com.example.vocabularytrainer.util.Constants

class HomePreferenceImpl(private val prefs: SharedPreferences): HomePreferences {
    override fun getMainGroupId(): String {
        return prefs.getString(Constants.KEY_ALL_GROUP_ID, "")!!
    }

    override fun setAllGroupId(id: String) {
        prefs
            .edit()
            .putString(Constants.KEY_ALL_GROUP_ID, id)
            .apply()
    }
}