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

import com.hyejeanmoon.splashcompose.utils.LogUtils
import com.hyejeanmoon.splashcompose.utils.MoonException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ApiEnqueueCallback
 *
 * It's a callback for retrofit. the effect of this class is catch exception and output log.
 */
class ApiEnqueueCallback<T>(
    private val onSuccess: ((response: T) -> Unit),
    private val onError: ((Exception) -> Unit),
    private val retryMaxCount: Int = 0
) : Callback<T> {

    private lateinit var call: Call<T>

    override fun onResponse(call: Call<T>, response: Response<T>) {
        this.call = call

        val body = response.body() ?: throw MoonException(
            message = "code = ${response.code().toString()} , message = ${
                response.errorBody()
                    .toString()
            }"
        )

        if (response.isSuccessful) {
            LogUtils.outputLog("response body: ${response.body()}")
            onSuccess(body)
        } else {
            onError(
                MoonException(
                    message = "code = $response.code().toString() , message = ${
                        response.errorBody()
                            .toString()
                    }"
                )
            )
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        LogUtils.outputLog(
            "ApiEnqueueCallback#onFailure: requestUrl=%s,  stateOfCanceled=%s",
            call.request().url().uri().toString(),
            call.isCanceled
        )

        LogUtils.outputLog(t)

        onError(MoonException(t))
    }
}