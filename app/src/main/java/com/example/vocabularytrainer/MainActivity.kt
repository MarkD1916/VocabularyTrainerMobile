package com.example.vocabularytrainer

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vocabularytrainer.data.preferences.AuthPreferenceImpl
import com.example.vocabularytrainer.extantions.navigateEvent
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.auth.AuthScreen
import com.example.vocabularytrainer.presentation.auth.RegisterScreen
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.detail_group.DetailGroupScreen
import com.example.vocabularytrainer.presentation.detail_group.DetailGroupViewModel
import com.example.vocabularytrainer.presentation.home.HomeScreen
import com.example.vocabularytrainer.presentation.home.HomeViewModel
import com.example.vocabularytrainer.presentation.home.LoadingType
import com.example.vocabularytrainer.presentation.home.components.AppBar
import com.example.vocabularytrainer.presentation.home.components.DrawerBody
import com.example.vocabularytrainer.presentation.home.components.DrawerHeader
import com.example.vocabularytrainer.presentation.home.components.MenuItem
import com.example.vocabularytrainer.presentation.welcome.WelcomeScreen
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme
import com.vmakd1916gmail.com.core.util.UiEvent

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private val groupDetailViewModel: DetailGroupViewModel by viewModels()


    @Inject
    lateinit var authPreference: AuthPreferenceImpl

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VocabularyTrainerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val showBottomBar = remember {
                    mutableStateOf(false)
                }
                val showAppBar = remember {
                    mutableStateOf(false)
                }
                val showFabButton = remember {
                    mutableStateOf(false)
                }
                val currentRoute by navController.currentBackStackEntryAsState()


                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        AnimatedVisibility(
                            visible = showBottomBar.value,
                            enter = scaleIn(),
                            exit = scaleOut(),
                        ) {
                            BottomAppBar(
                                cutoutShape = MaterialTheme.shapes.small.copy(
                                    CornerSize(percent = 50)
                                )
                            ) {
                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Default.Home, "")
                                    },
                                    label = { Text(text = "Save") },
                                    selected = true,
                                    onClick = {
                                    },
                                    alwaysShowLabel = false
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(Icons.Default.List, "")
                                    },


                                    label = { Text(text = "Upload") },
                                    selected = false,//selectedItem.value == "upload",
                                    onClick = {

                                    },
                                    alwaysShowLabel = false
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        val fabState = viewModel.state.fabState
                        var selected by remember { mutableStateOf(false) }
                        var finished by remember { mutableStateOf(false) }
                        var open by remember { mutableStateOf(false) }
                        val flashFinished: (Float) -> Unit = {
                            finished = true
                            if (finished && !selected) {
                                when (currentRoute?.destination?.route) {
                                    Route.HOME -> {
                                        viewModel.isOpen = true
                                    }
                                    Route.DETAIL_GROUP -> {
                                        groupDetailViewModel.isOpen = true
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

                        AnimatedVisibility(
                            visible = showFabButton.value,
                            enter = scaleIn(),
                            exit = scaleOut(),
                        ) {
                            FloatingActionButton(
                                modifier = Modifier
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
                                onClick = {}
                            ) {
                                when (fabState) {
                                    is LoadingType.FabLoading -> {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add group"
                                        )
                                    }
                                    else -> {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add group"
                                        )
                                    }
                                }
                            }
                        }
                    },

                    isFloatingActionButtonDocked = true,

                    floatingActionButtonPosition =when (currentRoute?.destination?.route) {
                        "detail_group/{groupId}" -> {
                            FabPosition.Center
                        }
                        Route.HOME -> {
                            FabPosition.Center
                        }
                        else -> FabPosition.Center
                    },
                    topBar = {
                        if (showAppBar.value) {
                            AppBar(
                                onNavigationIconClick = {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                }
                            )
                        }
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
                                when (it.id) {
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
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(
                            navController = navController,
                            startDestination = if (authPreference.getStoredToken()
                                    .isBlank()
                            ) Route.WELCOME else Route.HOME
                        ) {
                            composable(Route.WELCOME)
                            {
                                WelcomeScreen(onNavigate = navController::navigateEvent)
                                showBottomBar.value = false
                                showFabButton.value = false
                                showAppBar.value = false
                            }
                            composable(Route.LOGIN)
                            {
                                AuthScreen(onNavigate = navController::navigateEvent)
                                showBottomBar.value = false
                                showFabButton.value = false
                                showAppBar.value = false
                            }
                            composable(Route.REGISTER)
                            {
                                RegisterScreen(onNavigate = navController::navigateEvent)
                                showBottomBar.value = false
                                showFabButton.value = false
                                showAppBar.value = false
                            }
                            composable(Route.HOME)
                            {
                                showBottomBar.value = true
                                showFabButton.value = true
                                showAppBar.value = true
                                HomeScreen(
                                    onNavigate = navController::navigateEvent,
                                    viewModel
                                ) {
                                }

                            }

                            composable(
                                route = Route.DETAIL_GROUP + "/{groupId}",
                                arguments = listOf(
                                    navArgument("groupId") {
                                        type = NavType.StringType
                                    }
                                )
                            )
                            {
                                val groupId = it.arguments?.getString("groupId")!!
                                DetailGroupViewModel.groupId = groupId
                                DetailGroupScreen(
                                    groupId = groupId,
                                    onNavigate = navController::navigateEvent,
                                    viewModel = groupDetailViewModel
                                ){


                                }
                                showBottomBar.value = false
                                showFabButton.value = true


                            }
                        }
                    }
                }
            }
        }
    }
}


