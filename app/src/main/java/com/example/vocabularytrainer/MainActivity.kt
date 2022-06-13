package com.example.vocabularytrainer

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vocabularytrainer.extantions.navigateEvent
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.auth.AuthScreen
import com.example.vocabularytrainer.presentation.auth.RegisterScreen
import com.example.vocabularytrainer.presentation.welcome.WelcomeEvent
import com.example.vocabularytrainer.presentation.welcome.WelcomeScreen
import com.example.vocabularytrainer.presentation.welcome.WelcomeViewModel
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.cancellable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WelcomeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocabularyTrainerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME
                    ) {
                        composable(Route.WELCOME)
                        {
                            WelcomeScreen(onNavigate = navController::navigateEvent)
                        }
                        composable(Route.LOGIN)
                        {
                            AuthScreen()
                        }
                        composable(Route.REGISTER)
                        {
                            RegisterScreen(onNavigate = navController::navigateEvent)
                        }
                    }
                }
            }
        }
    }
}

