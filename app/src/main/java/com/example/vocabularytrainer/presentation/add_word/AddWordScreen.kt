package com.example.vocabularytrainer.presentation.add_word

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.vocabularytrainer.presentation.MainActivityViewModel
import com.example.vocabularytrainer.presentation.add_word.components.ExampleItem
import com.example.vocabularytrainer.presentation.components.BlockScreen
import com.example.vocabularytrainer.presentation.components.MultiFabState

@Composable
fun AddWordScreen(
    isBlockScreen: Boolean = true,
    exampleNumber: Int
) {

    val scrollState = rememberScrollState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(
                Modifier
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 20.dp)
                        .background(Color.LightGray)
                ) {


                    Text(
                        "Word", modifier = Modifier
                            .background(Color.Green)
                            .align(Alignment.CenterHorizontally)
                    )

                    BasicTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp)
                            .heightIn(min = 100.dp, max = 250.dp)
                            .background(Color.Red)
                    )

                    Text(
                        "Translate", modifier = Modifier
                            .background(Color.Green)
                            .align(Alignment.CenterHorizontally)
                    )

                    BasicTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp)
                            .heightIn(min = 100.dp, max = 250.dp)
                            .background(Color.Red)
                    )

                    Text(
                        "Transcription", modifier = Modifier
                            .background(Color.Green)
                            .align(Alignment.CenterHorizontally)
                    )

                    BasicTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp)
                            .heightIn(min = 80.dp, max = 250.dp)
                            .background(Color.Red)
                    )
                    Text(
                        "Example", modifier = Modifier
                            .background(Color.Green)
                            .align(Alignment.CenterHorizontally)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 250.dp)
                            .verticalScroll(scrollState)
                    ) {
                        repeat(exampleNumber){
                            ExampleItem(modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                                it.toString())
                        }
                    }
                    Text(
                        "Image", modifier = Modifier
                            .background(Color.Green)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        "Description", modifier = Modifier
                            .background(Color.Green)
                            .align(Alignment.CenterHorizontally)
                    )
                    BasicTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp)
                            .heightIn(min = 80.dp, max = 250.dp)
                            .background(Color.Red)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    AnimatedVisibility(isBlockScreen) {
        BlockScreen()
    }
}