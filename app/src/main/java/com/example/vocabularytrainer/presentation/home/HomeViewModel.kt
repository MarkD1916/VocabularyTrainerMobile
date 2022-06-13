package com.example.vocabularytrainer.presentation.home

import androidx.lifecycle.ViewModel
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authPreference: AuthPreference
) : ViewModel() {

    fun getToken(): String {
        return authPreference.getStoredToken()
    }
}