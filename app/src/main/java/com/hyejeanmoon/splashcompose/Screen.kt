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

package com.hyejeanmoon.splashcompose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(
    val route: String,
    @StringRes val stringId: Int,
    @DrawableRes val drawableId: Int
) {
    object Photos : Screen("Photos", R.string.screen_photos, R.drawable.ic_photos)
    object Collections :
        Screen("Collections", R.string.screen_collections, R.drawable.ic_collections)

    object Love : Screen("Favorite", R.string.screen_favorite, R.drawable.ic_love)
    object Settings : Screen("Settings", R.string.screen_settings, R.drawable.ic_settings)
}