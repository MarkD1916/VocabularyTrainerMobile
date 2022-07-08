package com.example.vocabularytrainer.presentation.detail_group

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.local.home.entity.WordEntity
import com.example.vocabularytrainer.data.preferences.HomePreferenceImpl
import com.example.vocabularytrainer.domain.detail_group.use_case.GroupDetailUseCase
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
            is DetailGroupEvent.GetAllWordsByGroup -> {
                state = state.copy(
                    screenState = event.loadingType
                )

                Log.d("LOL", "onDetailGroupEvent: $allId, $groupId")
                if (groupId == allId) {
                    getAllWords?.cancel()
                    val flow2 = groupDetailUseCase.getAllWords.execute()
                    val flow1 = groupDetailUseCase.getWordsByGroup.execute(groupId)
                    getAllWords = flow1.zip(flow2) { f1, f2 ->
                        val mutableWordsList = mutableListOf<WordEntity>()
                        f1.data?.forEach {
                            it.words.forEach {
                                mutableWordsList.add(it)
                            }
                        }
                        f2.forEach {
                            mutableWordsList.add(it)
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
                            it.data?.forEach {
                                state = state.copy(
                                    words = it.words,
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