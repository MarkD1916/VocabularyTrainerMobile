package com.example.vocabularytrainer.presentation.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorText(
    errorMessage: String,
    textColor: Color,
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(10.dp),
            imageVector = Icons.Default.Close,
            contentDescription = "error",
            tint = Color.Red
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = errorMessage,
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp
            ),
            color = textColor
        )

    }

}