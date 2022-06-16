package com.example.vocabularytrainer.presentation.home


data class HomeState(
    val actionResult_1: String = "1",
    val actionResult_2: String = "2",
    val actionState_1: Resource<String> = Resource.NoAction(),

    val actionState_2: Resource<String> = Resource.NoAction(),
) {
    fun getActionResult1(): String{

        return actionState_1.data?:""
    }

    fun getActionResult2(): String{

        return actionState_2.data?:""
    }
}