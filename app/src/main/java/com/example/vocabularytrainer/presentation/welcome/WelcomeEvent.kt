package com.example.vocabularytrainer.presentation.welcome

sealed class WelcomeEvent {
    data class OnStartClick(val route: String): WelcomeEvent()
}