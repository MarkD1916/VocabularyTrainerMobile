package com.example.vocabularytrainer.data.local.home

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vocabularytrainer.data.local.home.dao.VocabularyDao
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity

@Database(
    entities = [GroupEntity::class],
    version = 1
)
abstract class VocabularyDatabase: RoomDatabase(){
    abstract val dao: VocabularyDao
}