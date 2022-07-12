package com.example.vocabularytrainer.domain.detail_group.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.vocabularytrainer.presentation.home.Resource
import java.util.*

data class Word(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    val word: String,
    val translate: String,
    val transcription: String,
    val group_name: String,
    val isSync: Boolean,
    val isExpanded: Boolean = false,
    var wordState: Resource<*>
)
