package com.example.vocabularytrainer.presentation.auth.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthTextField(
    modifier: Modifier,
    labelText: String,
    placeHolderText: String,
    icon: ImageVector,
    text: String = "",
    onValueChange: (String)-> Unit,
    isError: Boolean,
    readOnly: Boolean,
    focusManager: FocusManager,
    focusDirection: FocusDirection,
    imeAction: ImeAction
) {
//    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        modifier = modifier,
        value = text,
        readOnly = readOnly,
        leadingIcon = { Icon(imageVector = icon, contentDescription = "emailIcon") },
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        isError = isError,
        placeholder = { Text(text = placeHolderText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = Color.Red,
            textColor = Color.Black
        ),
       textStyle = TextStyle(
           fontFamily = FontFamily.Default,
           fontWeight = FontWeight.Normal,
           fontSize = 15.sp
       ),
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(focusDirection) },
            onDone = {focusManager.clearFocus()}
        )
    )
}