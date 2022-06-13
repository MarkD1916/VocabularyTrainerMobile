package com.example.vocabularytrainer.presentation.welcome

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.navigation.Route
import com.vmakd1916gmail.com.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel@Inject constructor(): ViewModel() {
    var job: Job? by mutableStateOf(null)

    private val _uiEvent = Channel<UiEvent>()
    var uiEvent: Flow<UiEvent>? = _uiEvent.receiveAsFlow()
    val startNow = mutableStateOf(false)
    fun onEvent(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.OnStartClick -> {
                viewModelScope.launch {
                    _uiEvent.trySend(
                        UiEvent.Navigate(
                            route = event.route
                        )
                    )


                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}