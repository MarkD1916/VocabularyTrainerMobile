package com.example.vocabularytrainer.data.local.home.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "GroupTable")
data class GroupEntity(
    @PrimaryKey(autoGenerate = false) val id: String = UUID.randomUUID().toString(),
    val name: String,
    var isSync: Boolean,
    val date: Long
){

}
