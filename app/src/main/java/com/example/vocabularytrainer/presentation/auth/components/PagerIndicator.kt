package com.example.vocabularytrainer.presentation.auth.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi


data class PagerIndicatorSetup(
    val width: Float = 0f,
    val pageCount: Int = 1,
    val indicatorPadding: Int = 0
) {
    val listOfPositions: ArrayList<Int>
        get() = getPositions()

    private fun getPositions(): ArrayList<Int> {
        val list = arrayListOf<Int>()
        for (i in 0 until pageCount) {
            var p = (width - indicatorPadding) / (pageCount-i)
            if (i == 0) {
                p = 0f
            }

            list.add((p).toInt())
        }
        return list
    }


}


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicator(
    pagerPosition: Int = 0,
    targetPosition: Int = 1,
    isScrollInProcess: Boolean = false,
    positionOfIndicator: ArrayList<Int> = arrayListOf(),
    indicatorImageId: Int,
    indicatorSize: Dp
) {

    val offsetAnimation: Dp by animateDpAsState(
        if (!isScrollInProcess) positionOfIndicator[pagerPosition].dp else positionOfIndicator[targetPosition].dp
    ) {
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        ) {
            Image(
                painterResource(indicatorImageId),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(indicatorSize)
                    .absoluteOffset(x = if (isScrollInProcess) offsetAnimation else positionOfIndicator[pagerPosition].dp)

            )
        }

    }

}