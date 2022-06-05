package com.example.vocabularytrainer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vocabularytrainer.extantions.navigateEvent
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.auth.AuthScreen
import com.example.vocabularytrainer.presentation.auth.RegisterScreen
import com.example.vocabularytrainer.presentation.welcome.WelcomeScreen
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocabularyTrainerTheme {
                val navController = rememberAnimatedNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    scaffoldState = scaffoldState
                ) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Route.WELCOME
                    ) {
//                        composable(Route.WELCOME) {
//                            WelcomeScreen(onNavigate = navController::navigateEvent)
//                        }
//                        composable(Route.LOGIN) {
////                            navController.popBackStack()
//                            AuthScreen()
//                        }
                        composable(Route.WELCOME,
                            enterTransition = { null },
                            exitTransition = { null }
                        )
                        {
                            WelcomeScreen(onNavigate = navController::navigateEvent)
                        }
                        composable(Route.LOGIN,
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Route.WELCOME ->
                                        expandHorizontally (
                                            animationSpec = tween(500)
                                        )
                                    else -> null
                                }
                            },
                            exitTransition = {
                                when (targetState.destination.route) {
                                    Route.WELCOME ->
                                        shrinkHorizontally(
                                            animationSpec = tween(500)
                                        )
                                    else -> null
                                }
                            }
                        )
                        {
                            AuthScreen()
                        }
                        composable(Route.REGISTER,
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Route.WELCOME ->
                                        expandHorizontally (
                                            animationSpec = tween(500)
                                        )
                                    else -> null
                                }
                            },
                            exitTransition = {
                                when (targetState.destination.route) {
                                    Route.WELCOME ->
                                        shrinkHorizontally(
                                            animationSpec = tween(500)
                                        )
                                    else -> null
                                }
                            })
                        {
                            RegisterScreen()
//                            GenderScreen(onNavigate = navController::navigate)
                        }
                    }
                }
            }
        }
    }
}

