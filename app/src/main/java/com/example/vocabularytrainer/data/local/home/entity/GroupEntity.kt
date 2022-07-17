package com.example.vocabularytrainer.data.local.home.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "GroupTable")
data class GroupEntity(
    @ColumnInfo(name = "groupId")
    @PrimaryKey(autoGenerate = false) val id: String,
    val name: String,
    var isSync: Boolean
){

}
