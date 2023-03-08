/*
 * Copyright (C) 2021 HyejeanMOON.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyejeanmoon.splashcompose.api

import com.hyejeanmoon.splashcompose.EnvParameters
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