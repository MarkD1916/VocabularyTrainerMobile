package com.example.vocabularytrainer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

@Composable
fun BlockScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .alpha(0.6f)
        .background(Color.White)
        .clickable {  }
        ) {

    }
}