package com.teddy.common.extensions

import com.teddy.common.model.network.NetResult

import androidx.lifecycle.MutableLiveData
import com.teddy.common.model.network.DataResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import okhttp3.ResponseBody.create
import retrofit2.Response
import java.net.UnknownHostException

private const val DEFAULT_ERROR_MSG = "Something went wrong."

@ExperimentalCoroutinesApi
fun <T> Flow<NetResult<T>>.loading(): Flow<NetResult<T>> =
    this.onStart { emit(NetResult.StartLoading) }
        .onCompletion { emit(NetResult.StopLoading) }

@ExperimentalCoroutinesApi
suspend inline fun <T, R : NetResult<T>> Flow<R>.collectWithLoading(
    load: MutableLiveData<Boolean>,
    crossinline action: suspend (value: R) -> Unit
) = collect {
    when (it) {
        is NetResult.StartLoading -> load.value = true
        is NetResult.StopLoading -> load.value = false
        else -> action(it)
    }
}

fun <T, R> Response<T>.parseSimple(parseMethod: (T) -> R): NetResult<R> {
    val responseData = body()
    println(responseData)
    return if (isSuccessful && responseData != null)
        try {
            NetResult.Success(parseMethod(responseData))
        } catch (e: Exception) {
            NetResult.Error(Throwable(e.message))
        }
    else
        NetResult.Error(Throwable(DEFAULT_ERROR_MSG))
}

fun <T, R> Response<DataResponse<T>>.parse(parseMethod: (T) -> R): NetResult<R> {
    val responseData = body()?.data
    return if (isSuccessful && responseData != null)
        try {
            NetResult.Success(parseMethod(responseData))
        } catch (e: Exception) {
            NetResult.Error(Throwable(e.message))
        }
    else
        NetResult.Error(Throwable(DEFAULT_ERROR_MSG))
}

fun Response<ResponseBody>.evaluate(): NetResult<Boolean> =
    if (isSuccessful)
        NetResult.Success(true)
    else
        NetResult.Error(Throwable(DEFAULT_ERROR_MSG))

fun <T> Throwable.toHumanReadable(): Response<T> {
    return when (this) {
        is UnknownHostException -> Response.error(500, create(null, "Network Connection Unstable"))
        else -> Response.error(500, create(null, "Oops! Connection problem"))
    }
}