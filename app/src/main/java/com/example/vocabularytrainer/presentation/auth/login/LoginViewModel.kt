package com.example.vocabularytrainer.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val authPreference: AuthPreference
) : ViewModel() {

    init {
        if (authPreference.getStoredEmail().isNotBlank()
            && authPreference.getStoredPassword().isNotBlank()
        ) {
            val email = authPreference.getStoredEmail()
            val password = authPreference.getStoredPassword()
            viewModelScope.launch {
                authUseCases.loginUser.execute(password, email)
            }
        }
    }
}