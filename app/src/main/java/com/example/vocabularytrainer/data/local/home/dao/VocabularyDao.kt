package com.example.vocabularytrainer.data.local.home.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.local.home.entity.LocallyDeletedGroupID
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface VocabularyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: GroupEntity)

    @Query("SELECT * FROM GroupTable ORDER BY name DESC")
    fun selectAllGroups(): Flow<List<GroupEntity>>

    @Query("DELETE FROM GroupTable WHERE id = :groupId")
    suspend fun deleteGroupById(groupId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocallyDeletedGroupID(locallyDeletedGroupID: LocallyDeletedGroupID)

    @Query("DELETE FROM localy_deleted_group_ids WHERE deletedGroupID = :deletedGroupID")
    suspend fun deleteLocallyDeletedNoteID(deletedGroupID: String)

    @Query("SELECT * FROM localy_deleted_group_ids")
    suspend fun getAllLocallyDeletedGroupId(): List<LocallyDeletedGroupID>

    @Query("SELECT * FROM GroupTable WHERE isSync = 0 ORDER BY name DESC")
    suspend fun getAllUnsyncedGroups(): List<GroupEntity>

    @Query("DELETE FROM grouptable")
    suspend fun deleteAllGroups()

    @Query("SELECT MAX(id) FROM grouptable")
    suspend fun getMaxGroupId(): String

}