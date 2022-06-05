package com.example.vocabularytrainer.presentation.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.presentation.auth.components.AuthTextField
import com.example.vocabularytrainer.presentation.auth.components.PagerIndicator
import com.example.vocabularytrainer.presentation.auth.components.PagerIndicatorSetup
import com.example.vocabularytrainer.presentation.welcome.components.ImageWithSmallText
import com.example.vocabularytrainer.util.pxToDp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun RegisterScreen() {

    val pagerState = rememberPagerState()
    var size by remember { mutableStateOf(Size.Zero) }
    val t = PagerIndicatorSetup(
        width = pxToDp(size.width),
        pageCount = pagerState.pageCount,
        indicatorPadding = 60
    ).listOfPositions


    val loginImageState = remember {
        mutableStateOf(false)
    }
    Column {
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

                if (!t.isNullOrEmpty()) {
                    PagerIndicator(
                        pagerPosition = pagerState.currentPage,
                        targetPosition = pagerState.targetPage,
                        isScrollInProcess = pagerState.isScrollInProgress,
                        positionOfIndicator = t,
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
private fun EmailPasswordSubView(updateImage: (Boolean) -> Unit) {

        Row(Modifier
            .fillMaxSize()){
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
                    icon = Icons.Default.Email
                )

                AuthTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    labelText = "Password",
                    placeHolderText = "Enter your password",
                    Icons.Default.Lock
                )

                AuthTextField(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    labelText = "Confirm Password",
                    placeHolderText = "Confirm your password",
                    Icons.Default.Lock
                )
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