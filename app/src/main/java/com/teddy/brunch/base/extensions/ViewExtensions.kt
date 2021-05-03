package com.teddy.brunch.base.extensions

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafely(resId: Int, args: Bundle? = null) {
    try {
        navigate(resId, args)
    } catch (e: Exception) {
        Log.e("[BRUNCH]", e.message ?: "navigateSafely Error")
    }
}

fun NavController.navigateSafely(navDirections: NavDirections) {
    try {
        navigate(navDirections)
    } catch (e: Exception) {
        Log.e("[BRUNCH]", e.message ?: "navigateSafely Error")
    }
}