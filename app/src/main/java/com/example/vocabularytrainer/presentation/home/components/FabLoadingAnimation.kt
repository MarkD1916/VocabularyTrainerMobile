package com.example.vocabularytrainer.presentation.home.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun FabLoadingAnimation(
    percentage: Float = 10f,
    number: Int = 10,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    duration: Int = 4000,
    animDelay: Int = 0
) {


    val currentPercentage = remember { Animatable(0f) }



    LaunchedEffect(percentage) {

        currentPercentage.animateTo(

            percentage,

//            animationSpec = tween(durationMillis = duration, delayMillis = animDelay),
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1200
                },
                repeatMode = RepeatMode.Restart

            )
        )

    }


    Box(
        modifier = Modifier.size(radius * 2)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                -90f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}