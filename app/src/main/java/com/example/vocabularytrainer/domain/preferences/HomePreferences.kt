package com.example.vocabularytrainer.domain.preferences

interface HomePreferences {
    fun getAllGroupId(): String

    fun setAllGroupId(id: String)
}