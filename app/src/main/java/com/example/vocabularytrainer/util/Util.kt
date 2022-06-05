package com.example.vocabularytrainer.util

import android.content.res.Resources

fun pxToDp(px: Float): Float {
    return (px / Resources.getSystem().displayMetrics.density)
}