package com.hyejeanmoon.splashcompose.utils

/**
 * MoonException
 *
 * It's a custom exception that inherit from Exception.
 */
class MoonException(
    cause: Throwable? = null,
    message: String? = null
) : Exception(message, cause) {
    override fun toString(): String {

        return "${this::class.java.simpleName} cause=${cause?.message}  message=${message} "
    }
}