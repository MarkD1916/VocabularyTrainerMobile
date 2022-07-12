package com.example.vocabularytrainer.data.local.home.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(
    tableName = "WordTable",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["groupId"],
            childColumns = ["groupId"],
            onDelete = CASCADE //<<<<<
        )
    ])
data class WordEntity(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "groupId") val groupId: String,
    val word: String,
    val translate: String,
    val group_name: String,
    val isSync: Boolean,
    val transcription: String
)
