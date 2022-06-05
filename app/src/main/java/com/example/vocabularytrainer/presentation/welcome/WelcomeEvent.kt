package com.example.vocabularytrainer.presentation.welcome

sealed class WelcomeEvent {
    object OnGitHubButtonClick: WelcomeEvent()
    object OnWebButtonClick: WelcomeEvent()
}