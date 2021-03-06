package com.example.vocabularytrainer.presentation.components

import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.MainActivityViewModel
import com.example.vocabularytrainer.presentation.detail_group.DetailGroupEvent

enum class MultiFabState {
    COLLAPSED, EXPANDED
}


@OptIn(ExperimentalComposeUiApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Fab(
    currentRoute: NavBackStackEntry?,
    viewModel: MainActivityViewModel
) {


    var selected by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }
    var open by remember { mutableStateOf(false) }
    val flashFinished: (Float) -> Unit = {
        finished = true
        if (finished && !selected) {
            when (currentRoute?.destination?.route) {
                Route.HOME -> {
                    viewModel.isAddGroupDialogOpen = true
                }
                Route.DETAIL_GROUP + "/{groupId}" -> {
                    viewModel.onGlobalDetailGroupEvent(DetailGroupEvent.OnNewWordClick)
                }
            }

        }
    }
    val scale = animateFloatAsState(
        if (selected) 0.8f else 1.0f,
        tween(durationMillis = 150),
        finishedListener = flashFinished
    )
    val selectedItem = remember { mutableStateOf("home") }


    FloatingActionButton(
        modifier = Modifier
            .padding(end = 10.dp)
            .scale(scale.value)
            .pointerInteropFilter {
                selected = when (it.action) {
                    MotionEvent.ACTION_UP -> {
                        false
                    }
                    MotionEvent.ACTION_DOWN -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
                true
            },
        onClick = {},
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add group"
            )

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MultiFab(
    currentRoute: NavBackStackEntry?,
    viewModel: MainActivityViewModel,
    toState: MultiFabState,
    stateChanged: (fabState: MultiFabState) -> Unit,
    content: @Composable () -> Unit,
) {
    val transition = updateTransition(targetState = viewModel.fabButtonState, label = "")
    val rotation: Float by transition.animateFloat(label = "") { state ->
        if (state == MultiFabState.EXPANDED) 45f else 0f
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(transition.currentState == MultiFabState.EXPANDED) {
            content()
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(end = 10.dp)
                .align(Alignment.End),
            onClick = {
                viewModel.screenState = !viewModel.screenState

                if (transition.currentState == MultiFabState.EXPANDED) {
                    viewModel.fabButtonState = MultiFabState.COLLAPSED
                } else viewModel.fabButtonState = MultiFabState.EXPANDED

            }) {

            Icon(
                modifier = Modifier
                    .rotate(rotation),
                imageVector = Icons.Default.Add,
                contentDescription = "Add group"
            )
        }
    }


}