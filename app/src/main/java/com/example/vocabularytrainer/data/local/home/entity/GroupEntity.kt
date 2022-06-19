package com.example.vocabularytrainer.data.local.home.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GroupTable")
data class GroupEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val isSync: Boolean
)
