package com.example.vocabularytrainer.presentation.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.vocabularytrainer.R
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
                    imageId = R.drawable.ic_baseline_lock_open_24,
                    text = "Login\nand\npassword",
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                )
                ImageWithSmallText(
                    imageId = R.drawable.ic_baseline_add_avatar,
                    text = "Language\nand\navatar",
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )

                ImageWithSmallText(
                    imageId = R.drawable.ic_baseline_3p_24,
                    text = "\nPreview\n",
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    }
                )
            }

        }

        PagerContent(pagerState = pagerState)

    }
}


@OptIn(ExperimentalPagerApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
private fun PagerContent(pagerState: PagerState) {
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
                    EmailPasswordSubView()
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
private fun EmailPasswordSubView() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {


    }
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