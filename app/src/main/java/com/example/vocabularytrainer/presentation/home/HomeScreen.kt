package com.example.vocabularytrainer.presentation.home

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current


    Log.d("LOL", "HomeScreen: ${viewModel.getToken()}")

    Text(text = viewModel.getToken())


}