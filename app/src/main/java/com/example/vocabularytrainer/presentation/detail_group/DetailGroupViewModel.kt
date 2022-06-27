package com.example.vocabularytrainer.presentation.detail_group

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.mapper.home.toGroupSuccess
import com.example.vocabularytrainer.domain.detail_group.use_case.GroupDetailUseCase
import com.example.vocabularytrainer.domain.home.use_case.HomeUseCases
import com.example.vocabularytrainer.presentation.home.HomeEvent
import com.example.vocabularytrainer.presentation.home.HomeState
import com.example.vocabularytrainer.presentation.home.LoadingType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailGroupViewModel @Inject constructor(
    private val groupDetailUseCase: GroupDetailUseCase
): ViewModel() {

    var isOpen by mutableStateOf(false)
    var state by mutableStateOf(DetailGroupState())
    var getAllWords: Job? = null


    companion object{
        var groupId by mutableStateOf("")
    }

    init {

    }



    fun onDetailGroupEvent(event: DetailGroupEvent) {
        when (event) {
            is DetailGroupEvent.OnNewWordNativeEnter -> {
                state = state.copy(newWordNative = event.newWordNative)
            }
            is DetailGroupEvent.OnNewWordTranslateEnter -> {
                state = state.copy(newWordTranslate = event.newWordTranslate)
            }
            is DetailGroupEvent.GetAllWords -> {
                state = state.copy(
                    screenState = event.loadingType
                )
                getAllWords?.cancel()
                Log.d("LOL1", "onDetailGroupEvent: ${groupId}")
                getAllWords = groupDetailUseCase.getWordsByGroup.execute(groupId)
                    .onEach {
                        Log.d("LOL1", "onDetailGroupEvent: ${it?.data?.size}")
                        it.data?.forEach {
                            Log.d("LOL1", "onDetailGroupEvent: ${it?.words}")
                            state = state.copy(
                                words =  it.words ?: listOf(),
                                screenState = null
                            )
                        }
                    }
                    .flowOn(Dispatchers.IO)
                    .launchIn(viewModelScope)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("LOL1", "onCleared")
    }

}