package com.example.vocabularytrainer.data.local.home.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabularyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: GroupEntity)

    @Query("SELECT * FROM GroupTable ORDER BY id")
    fun selectAllGroups(): Flow<List<GroupEntity>>

}