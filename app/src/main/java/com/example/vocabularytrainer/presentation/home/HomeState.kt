package com.example.vocabularytrainer.presentation.home

import com.example.vocabularytrainer.data.local.home.entity.GroupEntity
import com.example.vocabularytrainer.data.remote.home.local.Item
import com.example.vocabularytrainer.data.remote.home.remote.response.GroupResponse
import com.example.vocabularytrainer.domain.home.model.Group


data class HomeState(
    val actionResult_1: String = "",
    var actionState_1: List<Item> = listOf(),
    var group: Resource<List<GroupEntity>> = Resource.NoAction()
) {

}