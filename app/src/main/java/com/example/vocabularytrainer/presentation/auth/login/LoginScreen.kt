package com.example.vocabularytrainer.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.auth.components.AuthTextField
import com.example.vocabularytrainer.presentation.auth.components.ErrorText
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginViewModel
import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.presentation.components.LoadAnimation
import com.example.vocabularytrainer.presentation.components.LoadingAnimationType
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.flow.collectIndexed

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = loginViewModel.state
    val (focusRequester) = FocusRequester.createRefs()
    val localFocusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(key1 = context) {

        loginViewModel.uiEvent?.collectIndexed { index, event ->

            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }

        }

    }

    when (state.loginResponseResult) {
        is AuthResponseResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LoadAnimation(
                    modifier =
                    Modifier
                        .align(Alignment.Center)
                        .size(300.dp),
                    radius = 200f,
                    stroke = 27f,
                    animationType = LoadingAnimationType.Screen
                )
            }
        }
        is AuthResponseResult.Success -> { }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        localFocusManager.clearFocus()
                    }
            ) {
                item {
                    Row(modifier = Modifier
                        .fillParentMaxSize()) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        ) {
                            AuthTextField(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .focusRequester(focusRequester),
                                labelText = "Email address",
                                placeHolderText = "Enter your e-mail",
                                icon = Icons.Default.Email,
                                text = state.email,
                                onValueChange = {
                                    loginViewModel.onEvent(AuthEvent.OnEmailEnter(it))
                                },
                                isError = state.emailError != null,
                                readOnly = false,
                                localFocusManager,
                                FocusDirection.Down,
                                ImeAction.Next
                            )

                            state.emailError?.let {
                                ErrorText(
                                    modifier = Modifier.padding(horizontal = 30.dp),
                                    errorMessage = it,
                                    textColor = Color.Red
                                )
                            }

                            AuthTextField(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .focusRequester(focusRequester),
                                labelText = "Password",
                                placeHolderText = "Enter your password",
                                Icons.Default.Lock,
                                text = state.password,
                                onValueChange = {
                                    loginViewModel.onEvent(AuthEvent.OnPasswordEnter(it))
                                },
                                isError = state.passwordError != null,
                                readOnly = false,
                                localFocusManager,
                                FocusDirection.Down,
                                ImeAction.Done
                            )

                            state.passwordError?.let {
                                ErrorText(
                                    modifier = Modifier.padding(horizontal = 30.dp),
                                    errorMessage = it,
                                    textColor = Color.Red
                                )
                            }
                            Button(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                onClick = {
                                    if (state.loginResponseResult == null || state.loginResponseResult is AuthResponseResult.Error) {
                                        loginViewModel.onEvent(LoginEvent.Login)
                                    }
                                    localFocusManager.clearFocus()
                                }
                            ) {
                                when (state.loginResponseResult) {
                                    null -> {
                                        Text(text = "Login", color = Color.White)
                                    }

                                    is AuthResponseResult.Error -> {
                                        Text(text = "Try Again", color = Color.White)
                                    }
                                }
                            }
                            if (state.loginResponseResult is AuthResponseResult.Error) {
                                ErrorText(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    errorMessage = state.loginResponseResult.message,
                                    textColor = Color.Red
                                )
                            }
                        }
                    }
                }
            }


        }
    }
}

