package com.example.vocabularytrainer.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LoadAnimation(){
    val infiniteTransition = rememberInfiniteTransition()

    val animationScale = infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                1500,
                0,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animationAlpha= infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                1500,
                0,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )


    Canvas(
        modifier = Modifier
            .scale(animationScale.value)
            .alpha(animationAlpha.value)
            .size(300.dp)
    ) {
        drawCircle(
            brush = Brush.sweepGradient(listOf(Color.Yellow, Color.Red)),
            radius = 300f,
            style = Stroke(90f)
        )

    }

}