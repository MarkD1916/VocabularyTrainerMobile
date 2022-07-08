package com.example.vocabularytrainer.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vocabularytrainer.presentation.components.DotLoadingAnimation


@Composable
fun GroupItem(
    modifier: Modifier,
    groupName: String,
    visible: Boolean,
    onDelete: () -> Unit,
    onToggleClick: () -> Unit,
    isSync: Boolean,
    isExpanded: Boolean,
    isMainGroup: Boolean = false,
    content: @Composable () -> Unit,
) {
    Card(
        elevation = 10.dp, modifier = modifier
            .padding(horizontal = 25.dp)
    ) {
        Column(modifier = modifier) {
            Column(modifier = modifier.clickable {
                onToggleClick()
            }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    if (visible) {
                        Text(
                            text = groupName,
                            modifier = Modifier
                                .widthIn(max = 150.dp)
                                .align(Alignment.CenterVertically)
                        )
                    } else {
                        DotLoadingAnimation(
                            modifier = Modifier
                                .widthIn(max = 150.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Column {
                        if (!isMainGroup) {
                            Button(
                                modifier = Modifier,
                                onClick = {
                                    onDelete()
                                }
                            ) {

                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .size(15.dp),
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "choose",
                                    tint = Color.White
                                )
                            }
                        }
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = if (isSync) "Sync" else "Not sync"
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
            }
            AnimatedVisibility(visible = isExpanded) {
                content()
            }
        }
    }
}