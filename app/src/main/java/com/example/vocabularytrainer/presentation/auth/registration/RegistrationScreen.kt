package com.example.vocabularytrainer.presentation.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.presentation.auth.components.AuthTextField
import com.example.vocabularytrainer.presentation.auth.components.ErrorText
import com.example.vocabularytrainer.presentation.auth.components.PagerIndicator
import com.example.vocabularytrainer.presentation.auth.components.PagerIndicatorSetup
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationEvent
import com.example.vocabularytrainer.presentation.auth.registration.RegistrationViewModel
import com.example.vocabularytrainer.presentation.auth.registration.ValidationEvent
import com.example.vocabularytrainer.presentation.welcome.components.ImageWithSmallText
import com.example.vocabularytrainer.util.Constants.PASSWORD_REQUIRE
import com.example.vocabularytrainer.util.pxToDp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen(

) {
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState()
    var size by remember { mutableStateOf(Size.Zero) }
    val indicatorSetup = PagerIndicatorSetup(
        width = pxToDp(size.width),
        pageCount = pagerState.pageCount,
        indicatorPadding = 60
    ).listOfPositions


    val loginImageState = remember {
        mutableStateOf(false)
    }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) {
            focusManager.clearFocus()
        }
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
                    horizontal = 15.dp
                )
        ) {
            Row(modifier = Modifier
                .padding(horizontal = 15.dp)
                .onGloballyPositioned { coordinates ->
                    size = coordinates.size.toSize()
                }
                .fillMaxWidth()
            ) {

                if (!indicatorSetup.isNullOrEmpty()) {
                    PagerIndicator(
                        pagerPosition = pagerState.currentPage,
                        targetPosition = pagerState.targetPage,
                        isScrollInProcess = pagerState.isScrollInProgress,
                        positionOfIndicator = indicatorSetup,
                        indicatorImageId = R.drawable.ic_baseline_local_pizza_24,
                        indicatorSize = 30.dp
                    )
                }

            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(
                        horizontal = 15.dp
                    )
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
            ) {
                val scope by mutableStateOf(rememberCoroutineScope())
                ImageWithSmallText(
                    imageIdFirstState = R.drawable.ic_baseline_lock_open_24,
                    imageIdSecondState = R.drawable.ic_baseline_lock_24,
                    imageState = loginImageState.value,
                    text = "Login\nand\npassword",
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                )
                ImageWithSmallText(
                    imageIdFirstState = R.drawable.ic_baseline_add_avatar,
                    text = "Language\nand\navatar",
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )

                ImageWithSmallText(
                    imageIdFirstState = R.drawable.ic_baseline_3p_24,
                    text = "\nPreview\n",
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

        }

        PagerContent(pagerState = pagerState) {
            loginImageState.value = it
        }

    }
}


@OptIn(ExperimentalPagerApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun PagerContent(
    pagerState: PagerState,
    updateImage: (Boolean) -> Unit
) {
    CompositionLocalProvider(
        LocalOverScrollConfiguration provides null
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = 3,
            state = pagerState
        ) { pager ->

            when (pager) {
                0 -> {
                    EmailPasswordSubView(updateImage)
                }
                1 -> {
                    AvatarNativeLanguageSubView()
                }
                2 -> {
                    CongratSubView()
                }
            }

        }
    }
}

@Composable
private fun EmailPasswordSubView(
    updateImage: (Boolean) -> Unit,
    registrationViewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = registrationViewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        registrationViewModel.validationEvents.collect { event ->
            when (event) {
                is ValidationEvent.Success -> {

                }
            }
        }
    }


    Row(
        Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 50.dp)
        ) {
            AuthTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                labelText = "Email address",
                placeHolderText = "Enter your e-mail",
                icon = Icons.Default.Email,
                text = state.email,
                onValueChange = {
                    registrationViewModel.onEvent(RegistrationEvent.OnEmailEnter(it))
                },
                isError = state.emailError != null
            )

            state.emailError?.let {
                ErrorText(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    errorMessage = state.emailError ?: "",
                    textColor = Color.Red
                )
            }


            AuthTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                labelText = "Password",
                placeHolderText = "Enter your password",
                Icons.Default.Lock,
                text = state.password,
                onValueChange = {
                    registrationViewModel.onEvent(RegistrationEvent.OnPasswordEnter(it))
                },
                isError = state.passwordError != null
            )

            Row(
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(10.dp),
                    imageVector = Icons.Default.Info,
                    contentDescription = "error"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = PASSWORD_REQUIRE,
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp
                    )
                )
            }

            state.passwordError?.let {
                ErrorText(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    errorMessage = it,
                    textColor = Color.Red
                )
            }
            AuthTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                labelText = "Confirm Password",
                placeHolderText = "Confirm your password",
                Icons.Default.Lock,
                text = state.confirmPassword,
                onValueChange = {
                    registrationViewModel.onEvent(RegistrationEvent.OnConfirmPasswordEnter(it))
                },
                isError = state.confirmPasswordError != null
            )
            state.confirmPasswordError?.let {
                ErrorText(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    errorMessage = it,
                    textColor = Color.Red
                )
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    registrationViewModel.onEvent(RegistrationEvent.Submit)
                }
            ) {

            }
        }
    }

//        Button(modifier = Modifier,
//            onClick = {
//                updateImage(true)
//            }
//        ) {
//
//        }


}

@Composable
private fun AvatarNativeLanguageSubView() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {

    }
}

@Composable
private fun CongratSubView() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {

    }
}

@Composable
private fun TestView() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {

    }
}