package com.hyejeanmoon.splashcompose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(
    val route: String,
    @StringRes val stringId: Int,
    @DrawableRes val drawableId: Int
) {
    object Photo : Screen("Photo", R.string.screen_photo, R.drawable.ic_photo)
    object Collections :
        Screen("Collections", R.string.screen_collections, R.drawable.ic_collections)

    object Love : Screen("Favorite", R.string.screen_favorite, R.drawable.ic_love)
    object Settings : Screen("Settings", R.string.screen_settings, R.drawable.ic_settings)
}