package com.example.vocabularytrainer.presentation.detail_group.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vocabularytrainer.presentation.components.DotLoadingAnimation

@Composable
fun WordItem(
    modifier: Modifier,
    word: String,
    translate: String,
    transcription: String,
    visible: Boolean,
    onDelete: () -> Unit,
    onToggleClick: () -> Unit,
    isSync: Boolean,
    isExpanded: Boolean,
    content: @Composable () -> Unit,
) {
    Card(
        elevation = 10.dp, modifier = modifier
            .padding(horizontal = 25.dp)
            .clickable {
                onToggleClick()
            }
    ) {
        Column(modifier = modifier) {
            Column(
                modifier = modifier
                    .padding(start = 15.dp)
            ) {

                if (visible) {
                    Text(
                        text = "Word: $word",
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Translate: $translate",
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Transcription: ${transcription.ifBlank { "No transcription" }}",
                        modifier = Modifier
                    )
                } else {
                    DotLoadingAnimation(
                        modifier = Modifier
                            .widthIn(max = 150.dp)

                    )
                }

            }

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(35.dp),
                imageVector = if (!isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "choose",
                tint = Color.Black
            )

            AnimatedVisibility(visible = isExpanded) {
                content()
            }
        }
    }
}