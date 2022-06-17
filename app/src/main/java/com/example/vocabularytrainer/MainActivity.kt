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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vocabularytrainer.data.preferences.AuthPreference
import com.example.vocabularytrainer.extantions.navigateEvent
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.auth.AuthScreen
import com.example.vocabularytrainer.presentation.auth.RegisterScreen
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.auth.login.LoginViewModel
import com.example.vocabularytrainer.presentation.home.HomeScreen
import com.example.vocabularytrainer.presentation.home.HomeViewModel
import com.example.vocabularytrainer.presentation.home.components.AppBar
import com.example.vocabularytrainer.presentation.home.components.DrawerBody
import com.example.vocabularytrainer.presentation.home.components.DrawerHeader
import com.example.vocabularytrainer.presentation.home.components.MenuItem
import com.example.vocabularytrainer.presentation.welcome.WelcomeEvent
import com.example.vocabularytrainer.presentation.welcome.WelcomeScreen
import com.example.vocabularytrainer.presentation.welcome.WelcomeViewModel
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme
import com.vmakd1916gmail.com.core.util.UiEvent

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var authPreference: AuthPreference

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocabularyTrainerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if(authPreference.getStoredToken().isBlank()) Route.WELCOME else Route.HOME
                    ) {
                        composable(Route.WELCOME)
                        {
                            WelcomeScreen(onNavigate = navController::navigateEvent)
                        }
                        composable(Route.LOGIN)
                        {
                            AuthScreen(onNavigate = navController::navigateEvent)
                        }
                        composable(Route.REGISTER)
                        {
                            RegisterScreen(onNavigate = navController::navigateEvent)
                        }
                        composable(Route.HOME)
                        {
                                Scaffold(
                                    scaffoldState = scaffoldState,
                                    topBar = {
                                        AppBar(
                                            onNavigationIconClick = {
                                                scope.launch {
                                                    scaffoldState.drawerState.open()
                                                }
                                            }
                                        )
                                    },
                                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                                    drawerContent = {
                                        DrawerHeader()
                                        DrawerBody(
                                            items = listOf(
                                                MenuItem(
                                                    id = "home",
                                                    title = "Home",
                                                    contentDescription = "Go to home screen",
                                                    icon = Icons.Default.Home
                                                ),
                                                MenuItem(
                                                    id = "settings",
                                                    title = "Settings",
                                                    contentDescription = "Go to settings screen",
                                                    icon = Icons.Default.Settings
                                                ),
                                                MenuItem(
                                                    id = "help",
                                                    title = "Help",
                                                    contentDescription = "Get help",
                                                    icon = Icons.Default.Info
                                                ),
                                                MenuItem(
                                                    id = "logout",
                                                    title = "Logout",
                                                    contentDescription = "Logout",
                                                    icon = Icons.Default.Close
                                                ),
                                            ),
                                            onItemClick = {
                                                when(it.id) {
                                                    "logout" -> {
                                                        scope.launch {
                                                            scaffoldState.drawerState.close()
                                                        }
                                                        viewModel.onEvent(LoginEvent.LogOut)
                                                        navController.navigateEvent(
                                                            UiEvent.Navigate(
                                                                Route.WELCOME
                                                            )
                                                        )

                                                    }

                                                }
                                            }
                                        )
                                    }
                                ) {
                                    HomeScreen(onNavigate = navController::navigateEvent)
                                }


                        }
                    }
                }
            }
        }
    }
}

