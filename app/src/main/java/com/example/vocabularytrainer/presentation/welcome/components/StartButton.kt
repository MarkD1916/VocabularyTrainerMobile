package com.example.vocabularytrainer.presentation.welcome.components

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import com.example.vocabularytrainer.presentation.welcome.WelcomeEvent
import com.example.vocabularytrainer.presentation.welcome.WelcomeViewModel
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.CoroutineScope

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
    var selected by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }
    val flashFinished: (Float) -> Unit = {
        finished = true
        if (finished && !selected) {
            viewModel.onEvent(WelcomeEvent.OnStartClick(route))
        }
    }
    val scale = animateFloatAsState(
        if (selected) 1.1f else 1f,
        tween(durationMillis = 150),
        finishedListener = flashFinished
    )


    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .heightIn(max = 155.dp)
            .height(155.dp)
            .scale(scale.value)

            .pointerInteropFilter {

                when (it.action) {

                    MotionEvent.ACTION_UP -> {

                        selected = false

                    }
                    MotionEvent.ACTION_DOWN -> {
                        selected = true
                    }
                    else -> {


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