package com.example.vocabularytrainer.presentation.home

import com.example.vocabularytrainer.data.remote.home.local.Item
import com.example.vocabularytrainer.domain.home.model.Group
import java.util.*
import kotlin.collections.ArrayList


data class HomeState(
    val actionResult_1: String = "",
    var actionState_1: List<Item> = listOf(),

    var group: List<Group> = listOf(),

    var screenState: Resource<*>? = null,

    var fabState: Resource<*>? = Resource.NoAction(null),

    var newGroupName: String = ""
) {

}


data class NewGroup(val newGroupName: String = "", val id: String = UUID.randomUUID().toString())