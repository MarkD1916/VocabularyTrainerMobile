package com.example.vocabularytrainer.domain.repository

interface SyncController {
    suspend fun syncGroups()

    suspend fun syncWords(groupId: String)

    suspend fun syncGroupsAndWords()
}