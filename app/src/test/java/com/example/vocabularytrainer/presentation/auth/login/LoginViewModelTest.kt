package com.example.vocabularytrainer.presentation.auth.login

import com.example.vocabularytrainer.presentation.auth.AuthEvent
import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult
import com.example.vocabularytrainer.data.preferences.AuthPreferenceImpl
import com.example.vocabularytrainer.data.preferences.HomePreferenceImpl
import com.example.vocabularytrainer.domain.auth.use_case.AuthUseCases
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test



class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var authUseCases: AuthUseCases

    @MockK
    private lateinit var homePreferenceImpl: HomePreferenceImpl

    @MockK
    private lateinit var authPreferenceImpl: AuthPreferenceImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = LoginViewModel(authUseCases,authPreferenceImpl, homePreferenceImpl)
    }

    @Test
    fun test() {
        viewModel.onEvent(AuthEvent.Loading)
        assertEquals(viewModel.state.loginResponseResult, AuthResponseResult.Loading)
    }
}