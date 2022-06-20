package com.example.vocabularytrainer.domain.home.model

import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.presentation.home.Resource

data class Group(
    val id: String,
    val name: String,
    val isSync: Boolean,
    val state: Resource<*>
)
