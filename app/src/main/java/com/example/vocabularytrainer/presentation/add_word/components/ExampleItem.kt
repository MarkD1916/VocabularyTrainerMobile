package com.example.vocabularytrainer.presentation.add_word.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExampleItem(
    modifier: Modifier,
    example: String
) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {


                    BasicTextField(
                        value = example,
                        onValueChange = {},
                        modifier = Modifier
                            .widthIn(max = 220.dp)
                            .heightIn(min = 100.dp, max = 250.dp)
                            .background(Color.Red)
                            .align(Alignment.CenterVertically)
                    )



                    Button(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = {

                        }
                    ) {

                        Icon(
                            modifier = Modifier
                                .size(15.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "choose",
                            tint = Color.White
                        )
                    }


                }

            }


