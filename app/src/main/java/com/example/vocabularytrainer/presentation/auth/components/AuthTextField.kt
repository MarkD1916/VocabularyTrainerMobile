package com.example.vocabularytrainer.presentation.auth.components

import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun AuthTextField(
    modifier: Modifier,
    labelText: String,
    placeHolderText: String,
    icon: ImageVector,
    text: String = "",
    onValueChange: (String)-> Unit,
    isError: Boolean,
) {
//    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        modifier = modifier,
        value = text,
        leadingIcon = { Icon(imageVector = icon, contentDescription = "emailIcon") },
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        isError = isError,
        placeholder = { Text(text = placeHolderText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = Color.Red
        )
    )
}