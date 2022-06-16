package com.example.vocabularytrainer.presentation.home

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.flow.collectIndexed

@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Log.d("LOL", "HomeScreen: ${viewModel.hashCode()}")
    LaunchedEffect(key1 = context) {

        viewModel.uiEvent?.collectIndexed { index, event ->

            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }

        }

    }
    Log.d("LOL", "HomeScreen: ${viewModel.getToken()}")

    Text(text = viewModel.getToken())


}