package com.example.vocabularytrainer.domain.repository

interface SyncController {
    suspend fun syncGroups()


    suspend fun syncGroupsAndWords()
}