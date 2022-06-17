package com.example.vocabularytrainer.data.remote.home.local

import com.example.vocabularytrainer.presentation.home.Resource


data class Item(
    val id: Int,
    var state: Resource<String>
)

object Test {
    val list = listOf(
        Item(0,Resource.NoAction()),
        Item(1,Resource.NoAction()),
        Item(2,Resource.NoAction()),
        Item(3,Resource.NoAction()),
        Item(4,Resource.NoAction()),
        Item(5,Resource.NoAction()),
        Item(6,Resource.NoAction()),
        Item(7,Resource.NoAction()),
        Item(8,Resource.NoAction()),
        Item(9,Resource.NoAction()),
        Item(10,Resource.NoAction()),
    )
}