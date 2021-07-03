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