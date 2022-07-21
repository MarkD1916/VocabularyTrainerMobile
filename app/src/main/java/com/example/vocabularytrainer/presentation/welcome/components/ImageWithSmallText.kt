package com.example.vocabularytrainer.presentation.welcome.components

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabularytrainer.R

@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun ImageWithSmallText(
    modifier: Modifier = Modifier,
    imageIdFirstState: Int,
    imageIdSecondState: Int = R.drawable.ic_baseline_web_24,
    text: String,
    onClick: () -> Unit,
    imageState: Boolean = false,
    textColor: Color = Color(0xFFFFFFFF),
    xOffset: Float = 0f,
    yOffset: Float = 0f
) {
    Row(modifier = Modifier
        .absoluteOffset(xOffset.dp, yOffset.dp)
        .width(70.dp)
        .clickable {
            onClick()
        }) {
        Crossfade(
            imageState,
            animationSpec = tween(500),
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painterResource(if (!it) imageIdFirstState else imageIdSecondState),
                contentDescription = "",
                contentScale = ContentScale.Fit,

            )
        }
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            modifier = Modifier.align(Alignment.Top),
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp
            ),

            text = text,
            color = textColor
        )
    }
}