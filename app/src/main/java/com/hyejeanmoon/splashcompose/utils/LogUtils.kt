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

package com.hyejeanmoon.splashcompose.utils

import timber.log.Timber

/**
 * LogUtils
 *
 * It's a log utility.
 */
object LogUtils {

    init {
        Timber.plant(Timber.DebugTree())
    }

    @JvmStatic
    fun outputLog(message: String, vararg args: Any, tag: String = "SPLASH_COMPOSE_LOG") {
        Timber.tag(tag)
        Timber.i(message, *args)
    }


    @JvmStatic
    fun outputLog(throwable: Throwable, tag: String = "SPLASH_COMPOSE_LOG") {
        Timber.tag(tag)
        Timber.i(throwable)
    }

    @JvmStatic
    fun outputLog(
        throwable: Throwable,
        message: String,
        vararg args: Any,
        tag: String = "SPLASH_COMPOSE_LOG"
    ) {
        Timber.tag(tag)
        Timber.w(throwable, message, *args)
    }
}