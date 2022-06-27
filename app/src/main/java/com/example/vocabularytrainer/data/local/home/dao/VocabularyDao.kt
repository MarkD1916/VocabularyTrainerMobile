package com.example.vocabularytrainer.data.local.home.dao

import androidx.room.*
import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.local.home.entity.LocallyDeletedGroupID
import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.local.home.entity.relations.GroupWithWords
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface VocabularyDao {

    //Group QUERY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: GroupEntity)

    @Query("SELECT * FROM GroupTable ORDER BY name DESC")
    fun selectAllGroups(): Flow<List<GroupEntity>>

    @Query("DELETE FROM GroupTable WHERE groupId = :groupId")
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

    @Query("SELECT MAX(groupId) FROM grouptable")
    suspend fun getMaxGroupId(): String

    //Word QUERY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordEntity)

    @Transaction
    @Query("SELECT * FROM GroupTable WHERE groupId = :groupId")
    fun getGroupWithWords(groupId: String): Flow<List<GroupWithWords>>

    @Query("SELECT * FROM WordTable WHERE isSync = 0 ORDER BY word DESC")
    suspend fun getAllUnsyncedWords(): List<WordEntity>

    @Query("DELETE FROM wordtable")
    suspend fun deleteAllWords()

//    @Query("SELECT * FROM WordTable WHERE isSync = 0 ORDER BY word DESC")
//    suspend fun checkIsAllGroup()

}