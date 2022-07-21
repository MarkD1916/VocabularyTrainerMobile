package com.example.vocabularytrainer

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vocabularytrainer.data.preferences.AuthPreferenceImpl
import com.example.vocabularytrainer.extantions.navigateEvent
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.MainActivityViewModel
import com.example.vocabularytrainer.presentation.auth.AuthScreen
import com.example.vocabularytrainer.presentation.auth.RegisterScreen
import com.example.vocabularytrainer.presentation.auth.login.LoginEvent
import com.example.vocabularytrainer.presentation.components.Fab
import com.example.vocabularytrainer.presentation.components.MultiFab
import com.example.vocabularytrainer.presentation.components.MultiFabState
import com.example.vocabularytrainer.presentation.detail_group.DetailGroupScreen
import com.example.vocabularytrainer.presentation.detail_group.DetailGroupViewModel
import com.example.vocabularytrainer.presentation.home.HomeScreen
import com.example.vocabularytrainer.presentation.home.components.AppBar
import com.example.vocabularytrainer.presentation.home.components.DrawerBody
import com.example.vocabularytrainer.presentation.home.components.DrawerHeader
import com.example.vocabularytrainer.presentation.home.components.MenuItem
import com.example.vocabularytrainer.presentation.train.TrainScreen
import com.example.vocabularytrainer.presentation.welcome.WelcomeScreen
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme
import com.vmakd1916gmail.com.core.util.UiEvent

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authPreference: AuthPreferenceImpl

    val viewModel: MainActivityViewModel by viewModels()

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

                val currentRoute by navController.currentBackStackEntryAsState()
                val context = LocalContext.current


                LaunchedEffect(key1 = context) {
                    viewModel.uiEvent?.collect { event ->

                        when (event) {
                            is UiEvent.Navigate -> {
                                navController.navigateEvent(
                                    UiEvent.Navigate(
                                        Route.WELCOME
                                    )
                                )
                            }
                            else -> Unit
                        }

                    }
                }
                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomAppBar(
//                                cutoutShape = MaterialTheme.shapes.small.copy(
//                                    CornerSize(percent = 50)
//                                )
                        ) {
                            BottomNavigationItem(
                                icon = {
                                    Icon(Icons.Default.Home, "")
                                },
                                label = {  },
                                selected = true,
                                onClick = {
                                },
                                alwaysShowLabel = false
                            )

                            BottomNavigationItem(
                                icon = {
                                    Icon(Icons.Default.List, "")
                                },


                                label = {  },
                                selected = false,//selectedItem.value == "upload",
                                onClick = {

                                },
                                alwaysShowLabel = false
                            )
                        }
                    },
                    floatingActionButton = {
                        var toState by remember { mutableStateOf(MultiFabState.COLLAPSED) }

                        currentRoute?.destination?.route?.let {
                            when (it) {
                                Route.HOME -> {
                                    Fab(
                                        currentRoute,
                                        viewModel
                                    )
                                }

                                else -> {
                                    MultiFab(
                                        currentRoute,
                                        viewModel,
                                        toState,
                                        stateChanged = { state ->
                                            toState = state
                                        }
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(end = 10.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            OutlinedButton(
                                                onClick = { /*TODO*/ },
                                                modifier = Modifier.size(50.dp),  //avoid the oval shape
                                                shape = CircleShape,
                                                border = BorderStroke(
                                                    1.dp,
                                                    MaterialTheme.colors.primary
                                                ),
                                                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    contentColor = MaterialTheme.colors.primary
                                                )
                                            ) {
                                                Icon(
                                                    Icons.Default.Add,
                                                    contentDescription = "content description"
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(15.dp))
                                            OutlinedButton(
                                                onClick = { /*TODO*/ },
                                                modifier = Modifier.size(50.dp),  //avoid the oval shape
                                                shape = CircleShape,
                                                border = BorderStroke(
                                                    1.dp,
                                                    MaterialTheme.colors.primary
                                                ),
                                                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    contentColor = MaterialTheme.colors.primary
                                                )
                                            ) {
                                                Icon(
                                                    Icons.Default.Add,
                                                    contentDescription = "content description"
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(15.dp))

                                        }
                                    }
                                }
                            }
                        }
                    },

                    isFloatingActionButtonDocked = false,

                    floatingActionButtonPosition = FabPosition.End,
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
                                when (it.id) {
                                    "logout" -> {
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                        }
                                        viewModel.onEvent(LoginEvent.LogOut)
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
                                HomeScreen(
                                    onNavigate = navController::navigateEvent,
                                    mainActivityViewModel = viewModel
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
                                    onNavigate = navController::navigateEvent
                                ) {

                                }
                            }

                            composable(
                                route = Route.TRAIN + "/{groupId}",
                                arguments = listOf(
                                    navArgument("groupId") {
                                        type = NavType.StringType
                                    }
                                )
                            )
                            {
                                val groupId = it.arguments?.getString("groupId")!!
                                TrainScreen(
                                    groupId = groupId,
                                    onNavigate = navController::navigateEvent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


