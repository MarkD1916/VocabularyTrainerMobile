package com.example.vocabularytrainer.presentation.welcome

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.navigation.Route
import com.example.vocabularytrainer.presentation.welcome.components.ImageWithSmallText
import com.example.vocabularytrainer.presentation.welcome.components.StartButton
import com.example.vocabularytrainer.util.Constants
import com.vmakd1916gmail.com.core.util.UiEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@SuppressLint("UnrememberedMutableState")
@Composable
fun WelcomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val scope by mutableStateOf(rememberCoroutineScope())

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {

            viewModel.uiEvent?.collectIndexed { index, event ->

                    when (event) {
                        is UiEvent.Navigate -> onNavigate(event)
                        else -> Unit
                    }

            }

        }


    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 50.dp,
                            bottomStart = 50.dp
                        )
                    )
                    .background(MaterialTheme.colors.primary)
                    .padding(
                        horizontal = 15.dp,
                        vertical = 15.dp
                    )
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Welcome to Vocabulary Trainer APP",
                        color = Color(0xFFFFFFFF)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(
                            horizontal = 15.dp
                        )
                ) {
                    val uriGitHub: Uri =
                        Uri.parse(Constants.GIT_HUB_LINK)
                    val uriWebBlog: Uri =
                        Uri.parse(Constants.WEB_BLOG_LINK)
                    val uriHandler = LocalUriHandler.current
                    ImageWithSmallText(
                        imageIdFirstState = R.drawable.github_logo_white,
                        text = "My GitHub",
                        onClick = {
                            uriHandler.openUri(uriGitHub.toString())
                        }
                    )

                    ImageWithSmallText(
                        imageIdFirstState = R.drawable.ic_baseline_web_24,
                        text = "Web version",
                        onClick = {
                            uriHandler.openUri(uriWebBlog.toString())
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            Column(
                modifier =
                Modifier
                    .padding(horizontal = 15.dp, vertical = 20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {


                StartButton(
                    modifier = Modifier,
                    text = "I already have an Account",
                    imageId = R.drawable.ic_baseline_insert_emoticon_24,
                    onNavigate = onNavigate,
                    route = Route.LOGIN,
                    scope = scope,
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(15.dp))
                StartButton(
                    modifier = Modifier,
                    text = "Create new one",
                    imageId = R.drawable.ic_baseline_add_reaction_24,
                    onNavigate = onNavigate,
                    route = Route.REGISTER,
                    scope = scope,
                    viewModel = viewModel
                )
                Spacer(modifier = Modifier.height(15.dp))
                StartButton(
                    modifier = Modifier,
                    text = "Only Local usage",
                    imageId = R.drawable.db_local_icon_white,
                    onNavigate = onNavigate,
                    route = Route.REGISTER,
                    scope = scope,
                    viewModel = viewModel
                )
            }
        }
    }
}

