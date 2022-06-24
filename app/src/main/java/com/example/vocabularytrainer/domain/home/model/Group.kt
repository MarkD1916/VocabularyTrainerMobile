package com.example.vocabularytrainer.domain.home.model

import com.example.vocabularytrainer.presentation.home.Resource

data class Group(
    var id: String,
    val name: String,
    val isSync: Boolean,
    val state: Resource<*>,
    val date: Long,
    val isExpanded: Boolean = false
)
