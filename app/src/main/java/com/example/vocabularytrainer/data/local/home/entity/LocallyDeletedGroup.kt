package com.example.vocabularytrainer.data.local.home.entity

import androidx.room.PrimaryKey

data class LocallyDeletedGroup(
    @PrimaryKey(autoGenerate = false)
    val deletedNoteId: String
)
