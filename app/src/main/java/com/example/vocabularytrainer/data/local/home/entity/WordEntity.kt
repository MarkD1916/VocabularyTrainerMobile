package com.example.vocabularytrainer.data.local.home.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "WordTable")
data class WordEntity(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "groupId") val groupId: String,
    val word: String,
    val translate: String,
    val group_name: String,
    val isSync: Boolean
)
