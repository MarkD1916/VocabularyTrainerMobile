package com.example.vocabularytrainer.presentation.detail_group

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.mapper.home.toWord
import com.example.vocabularytrainer.data.preferences.HomePreferenceImpl
import com.example.vocabularytrainer.domain.detail_group.model.Word
import com.example.vocabularytrainer.domain.detail_group.use_case.GroupDetailUseCase
import com.example.vocabularytrainer.util.Constants.MAIN_GROUP_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailGroupViewModel @Inject constructor(
    private val groupDetailUseCase: GroupDetailUseCase,
    private val homeSharedPreferences: HomePreferenceImpl
) : ViewModel() {

    var isOpen by mutableStateOf(false)
    var state by mutableStateOf(DetailGroupState())
    var getWordsByGroup: Job? = null
    var getAllWords: Job? = null

    val allId = homeSharedPreferences.getAllGroupId()

    companion object {
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
                    getAllWords?.cancel()
                    val getAllWordsFlow = groupDetailUseCase.getAllWords.execute()
                    val getGroupsWithWordsFlow = groupDetailUseCase.getWordsByGroup.execute(groupId)
                    getAllWords = getGroupsWithWordsFlow.zip(getAllWordsFlow) { f1, f2 ->
                        val mutableWordsList = mutableListOf<Word>()
                        f1.data?.forEach { groupWithWords ->
                            groupWithWords.words.forEach { wordEntityFromWordWithGroup ->
                                if(wordEntityFromWordWithGroup.group_name != MAIN_GROUP_NAME) {
                                    val word = wordEntityFromWordWithGroup.toWord()
                                    mutableWordsList.add(word)
                                }
                            }
                        }
                        f2.forEach { allWords ->
                            val word = allWords.toWord()
                            mutableWordsList.add(word)
                        }
                        state = state.copy(
                            words = mutableWordsList.distinct(),
                            screenState = null
                        )
                    }.flowOn(Dispatchers.Default)
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
            is DetailGroupEvent.GetAllWordsByMainGroup -> {

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("LOL1", "onCleared")
    }

}