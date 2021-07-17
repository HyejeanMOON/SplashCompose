package com.hyejeanmoon.splashcompose.api

import com.hyejeanmoon.splashcompose.utils.EnvParameters
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * OkHttpClient
 *
 * It's a okHttpClient of retrofit.
 */
class SplashOkHttpClient {
    private var okHttpClient: OkHttpClient

    init {

        val okHttpBuilder = OkHttpClient.Builder()
        // setting timeout for okHttpClient
        okHttpBuilder.connectTimeout(CONNECT_TIMEOUT_SECOND.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(READ_TIMEOUT_SECOND.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(WRITE_TIMEOUT_SECOND.toLong(), TimeUnit.SECONDS)

        okHttpClient = okHttpBuilder.build()
    }

    val splashOkHttpClient: OkHttpClient
        get() = okHttpClient.newBuilder()
            .addInterceptor(ApiInterceptor(EnvParameters.CLIENT_ID)).build()

    companion object {
        private const val CONNECT_TIMEOUT_SECOND = 20
        private const val READ_TIMEOUT_SECOND = 32
        private const val WRITE_TIMEOUT_SECOND = 32
    }
}