package com.example.vocabularytrainer.presentation.welcome.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vocabularytrainer.R

@Composable
fun ImageWithSmallText(
    modifier: Modifier = Modifier,
    imageId: Int,
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color(0xFFFFFFFF),
    xOffset: Float = 0f,
    yOffset: Float = 0f
) {
    Row(modifier = Modifier
        .absoluteOffset(xOffset.dp, yOffset.dp)
        .width(70.dp)
        .clickable {
        onClick()
    }){
        Image(
            painterResource(imageId),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
        )
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