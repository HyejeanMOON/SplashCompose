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

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils {

    private val sharedPreferences: SharedPreferences

    constructor(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCE_DATA_NAME, Context.MODE_PRIVATE)
    }

    fun putString(name: String, data: String) {
        val editor = sharedPreferences.edit()
        editor.putString(name, data)
        editor.apply()
    }

    fun getString(name: String): String {
        return sharedPreferences.getString(name, "") ?: ""
    }

    fun putInt(name: String, position: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(name, position)
        editor.apply()
    }

    fun getInt(name: String): Int {
        return sharedPreferences.getInt(name, 0)
    }

    companion object {
        private const val SHARED_PREFERENCE_DATA_NAME = "SPLASH_COMPOSE"

        const val KEY_ORDER_BY = "SPLASH_COMPOSE_ORDER_BY"
        const val KEY_DISPLAY_RESOLUTION = "SPLASH_COMPOSE_DISPLAY_RESOLUTION"
    }
}