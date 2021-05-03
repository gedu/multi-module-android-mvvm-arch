package com.teddy.datasource.di

import com.teddy.common.BuildConfig
import com.teddy.datasource.local.RestaurantLocalSource
import com.teddy.datasource.remote.RestaurantRemoteSource
import com.teddy.datasource.services.RestaurantService
import kotlinx.coroutines.FlowPreview
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

@FlowPreview
val networkModule = module {
    single { getLoggingInterceptor() }

    single { getHttpClient(listOf(authInterceptor()), get()) }

    single { getRetrofit(get()) }

    single { getRestaurantService(get()) }

    single { RestaurantRemoteSource(get()) }

    single { RestaurantLocalSource() }
}

private fun authInterceptor(): Interceptor {
    return Interceptor { chain ->
        val original = chain.request()
        val originalHttp = original.url

        try {
            val url = originalHttp.newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
                .build()
            val requestBuilder = original.newBuilder().url(url)
            val newRequest = requestBuilder.build()
            chain.proceed(newRequest)

        } catch (error: SocketTimeoutException) {
            chain.proceed(chain.request())
        }
    }
}

private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

private fun getHttpClient(
    interceptors: List<Interceptor>,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val builder = OkHttpClient.Builder()
    interceptors.forEach {
        builder.addInterceptor(it)
    }
//    builder.addNetworkInterceptor(StethoWrapper().newInterceptor())
    builder.readTimeout(20, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG)
        builder.addInterceptor(loggingInterceptor)
    return builder.build()
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun getRestaurantService(retrofit: Retrofit): RestaurantService =
    retrofit.create(RestaurantService::class.java)