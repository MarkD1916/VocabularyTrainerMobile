package com.example.vocabularytrainer.presentation.auth.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vocabularytrainer.presentation.auth.Country
import com.example.vocabularytrainer.presentation.auth.registration.AuthResponseResult
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.presentation.components.LoadAnimation

@Composable
fun CountryItem(country: Country, modifier: Modifier, updateImage: (String)-> Unit) {
    Card(elevation = 10.dp, modifier = modifier.padding(horizontal = 25.dp)) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = country.name, modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

                Button(
                    modifier = Modifier,
                    onClick = {
                        updateImage(country.code)
                    }
                ) {

                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(15.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = "error",
                        tint = Color.White
                    )

                }
            }

        }
    }
}

