package com.example.vocabularytrainer.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

sealed class LoadingAnimationType {
    object Screen : LoadingAnimationType()
    object Button : LoadingAnimationType()
}

@Composable
fun LoadAnimation(
    modifier: Modifier = Modifier,
    radius: Float = 20f,
    stroke: Float = 7f,
    animationType: LoadingAnimationType = LoadingAnimationType.Button
) {
    val infiniteTransition = rememberInfiniteTransition()

    val animationScale = infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                1000,
                0,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animationAlpha = infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                600,
                400,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    when(animationType) {
        is LoadingAnimationType.Screen -> {
            Box(modifier = modifier) {
                Canvas(
                    modifier = modifier
                        .align(Alignment.Center)
                        .scale(animationScale.value)

                ) {
                    drawCircle(
                        brush = Brush.sweepGradient(listOf(Color.Yellow, Color.Red)),
                        style = Stroke(stroke),//27f
                        radius = radius//200f
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(animationAlpha.value),
                    text = "Loading...",
                    color = Color.Black
                )
            }

        }
        is LoadingAnimationType.Button -> {
            Column(modifier = modifier) {
                Spacer(modifier = Modifier.height(14.dp))
                Canvas(
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .scale(animationScale.value)

                ) {
                    drawCircle(
                        brush = Brush.sweepGradient(listOf(Color.Yellow, Color.Red)),
                        style = Stroke(stroke),//27f
                        radius = radius//200f
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = "Loading...",
                    color = Color.White
                )
            }
        }
    }


}