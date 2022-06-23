package com.example.vocabularytrainer.domain.home.use_case

import com.example.vocabularytrainer.data.mapper.home.toGroupRequest
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.home.model.Group
import com.example.vocabularytrainer.domain.repository.HomeRepository

class AddGroup(
    private val repository: HomeRepository,
    private val authPreference: AuthPreference
) {
    suspend fun execute(group: Group) {
        return repository.postGroup(group.toGroupRequest(authPreference.getUserId()))
    }
}