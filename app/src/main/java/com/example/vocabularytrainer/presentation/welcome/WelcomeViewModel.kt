package com.example.vocabularytrainer.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vmakd1916gmail.com.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel@Inject constructor(): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    var uiEvent: Flow<UiEvent>? = _uiEvent.receiveAsFlow()

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