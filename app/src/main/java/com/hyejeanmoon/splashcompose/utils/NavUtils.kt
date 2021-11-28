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