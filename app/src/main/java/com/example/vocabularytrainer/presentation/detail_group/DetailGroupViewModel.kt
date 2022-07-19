package com.example.vocabularytrainer.presentation.detail_group

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.mapper.home.toWord
import com.example.vocabularytrainer.data.preferences.HomePreferenceImpl
import com.example.vocabularytrainer.domain.detail_group.use_case.GroupDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailGroupViewModel @Inject constructor(
    private val groupDetailUseCase: GroupDetailUseCase,
    private val homeSharedPreferences: HomePreferenceImpl
) : ViewModel() {

    var state by mutableStateOf(DetailGroupState())
    var getWordsByGroup: Job? = null
    val allId = homeSharedPreferences.getMainGroupId()

    companion object {
        var groupId by mutableStateOf("")
    }

    init {
        onDetailGroupEvent(DetailGroupEvent.GetAllWordsByGroup)
    }

    fun onDetailGroupEvent(event: DetailGroupEvent) {
        when (event) {
            is DetailGroupEvent.OnToggleClick -> {
                state = state.copy(
                    words = state.words.map {
                        if (it.id == event.wordId) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is DetailGroupEvent.GetAllWordsByGroup -> {
                state = state.copy(
                    screenState = event.loadingType
                )

                if (groupId == allId) {
                    getWordsByGroup?.cancel()
                    getWordsByGroup = groupDetailUseCase.getAllWords.execute()
                        .onEach {
                            val wordList = it.map {
                                it.toWord()
                            }
                            state = state.copy(
                                words = wordList,
                                screenState = null
                            )
                        }
                        .flowOn(Dispatchers.IO)
                        .launchIn(viewModelScope)

                } else {
                    getWordsByGroup?.cancel()
                    getWordsByGroup = groupDetailUseCase.getWordsByGroup.execute(groupId)
                        .onEach {
                            it.data?.forEach { groupWithWords ->
                                val wordList = groupWithWords.words.map {
                                    it.toWord()
                                }
                                state = state.copy(
                                    words = wordList,
                                    screenState = null
                                )
                            }
                        }
                        .flowOn(Dispatchers.IO)
                        .launchIn(viewModelScope)
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
    }

}