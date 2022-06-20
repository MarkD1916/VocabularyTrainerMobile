package com.example.vocabularytrainer.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
    visible:Boolean,
    onDelete: () -> Unit) {
    Card(elevation = 10.dp, modifier = modifier.padding(horizontal = 25.dp)) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                if(visible)
                {
                    Text(
                        text = groupName,
                        modifier = Modifier.widthIn(max = 150.dp).align(Alignment.CenterVertically)
                    )
                }
                else{
                    DotLoadingAnimation(modifier =Modifier.widthIn(max = 150.dp).align(Alignment.CenterVertically))
                }

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

        }
    }
}