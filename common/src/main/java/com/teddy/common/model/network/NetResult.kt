package com.teddy.common.model.network

import android.util.Log

sealed class NetResult<out T> {

    object StartLoading : NetResult<Nothing>()

    object StopLoading : NetResult<Nothing>()

    data class Success<out T>(val data: T) : NetResult<T>()

    data class Error(val error: Throwable) : NetResult<Nothing>() {
        init {
            Log.e("[BRUNCH]", "NET ERROR: $error")
        }
    }
}

class DataResponse<T>(val data: T)