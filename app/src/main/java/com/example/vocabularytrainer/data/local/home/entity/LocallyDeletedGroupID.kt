package com.example.vocabularytrainer.data.local.home.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localy_deleted_group_ids")
data class LocallyDeletedGroupID(
    @PrimaryKey(autoGenerate = false)
    val deletedGroupId: String
)