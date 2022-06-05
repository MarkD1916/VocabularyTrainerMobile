package com.example.vocabularytrainer.presentation.welcome.components

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.welcome.WelcomeViewModel
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StartButton(
    modifier: Modifier,
    text: String,
    imageId: Int,
    onNavigate: (UiEvent.Navigate) -> Unit,
    route: String,
    scope: CoroutineScope,
    viewModel: WelcomeViewModel

) {
    val selected = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected.value) 1.1f else 1f)

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .heightIn(max = 155.dp)
            .height(155.dp)
            .scale(scale.value)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_UP -> {
                        selected.value = false
                        if (viewModel.job == null) {
                            viewModel.job = scope.launch {
                                delay(150)
                                onNavigate(UiEvent.Navigate(route))
                            }
                        }

                        //TODO: content into AnimationButton

                    }
                    MotionEvent.ACTION_DOWN -> {
                        selected.value = true
                    }
                    else -> {
                        selected.value = false
                    }
                }
                true
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = "scored icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = text,

                color = Color(0xFFFFFFFF),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}