package com.example.vocabularytrainer.domain.preferences

interface HomePreferences {
    fun getMainGroupId(): String

    fun setAllGroupId(id: String)
}