package com.example.vocabularytrainer.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.preferences.AuthPreferenceImpl
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.components.MultiFabState
import com.example.vocabularytrainer.presentation.detail_group.DetailGroupEvent
import com.vmakd1916gmail.com.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authPreference: AuthPreferenceImpl,
    private val authUseCases: AuthUseCases,
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    var uiEvent: Flow<UiEvent>? = _uiEvent.receiveAsFlow()

    val id by mutableStateOf(authPreference.getUserId())

    var isAddGroupDialogOpen by mutableStateOf(false)

    var fabButtonState by mutableStateOf(MultiFabState.COLLAPSED)

    var screenState by mutableStateOf(false)
    var exampleNumber by mutableStateOf(1)


    fun onGlobalAuthEvent(event: AuthEvent) {
        when (event) {
            is LoginEvent.LogOut -> {
                logOutUser()
            }
        }
    }

    fun onGlobalDetailGroupEvent(event: DetailGroupEvent) {
        when (event) {
            is DetailGroupEvent.OnNewWordClick -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _uiEvent.send(
                        UiEvent.Navigate(
                            route = Route.ADD_NEW_WORD
                        )
                    )
                }
            }
        }
    }


    private fun logOutUser() {
        viewModelScope.launch(Dispatchers.Main) {
            authPreference.clearStoredData()
            _uiEvent.send(
                UiEvent.Navigate(
                    route = Route.WELCOME
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}