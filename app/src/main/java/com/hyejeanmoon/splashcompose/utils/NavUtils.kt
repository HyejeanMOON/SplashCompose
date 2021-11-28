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

import android.net.Uri
import android.os.Parcelable
import androidx.navigation.NavHostController

object NavUtils {

    fun navTo(
        navCtrl: NavHostController,
        destinationName: String,
        args: Any? = null,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true,
    ) {

        var arguments = ""
        if (args != null) {
            when (args) {
                is Parcelable -> {
                    arguments = String.format("/%s", Uri.encode(args.toJson()))
                }
                is String -> {
                    arguments = String.format("/%s", args)
                }
                is Int -> {
                    arguments = String.format("/%s", args)
                }
                is Float -> {
                    arguments = String.format("/%s", args)
                }
                is Double -> {
                    arguments = String.format("/%s", args)
                }
                is Boolean -> {
                    arguments = String.format("/%s", args)
                }
                is Long -> {
                    arguments = String.format("/%s", args)
                }
            }
        }

        navCtrl.navigate("$destinationName$arguments") {
            if (backStackRouteName != null) {
                popUpTo(backStackRouteName) { saveState = true }
            }
            launchSingleTop = isLaunchSingleTop
            restoreState = needToRestoreState
        }
    }

    fun NavHostController.back() {
        navigateUp()
    }
}