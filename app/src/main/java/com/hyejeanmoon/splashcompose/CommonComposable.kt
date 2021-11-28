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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsScreen
import com.hyejeanmoon.splashcompose.screen.photos.PhotoScreen
import com.hyejeanmoon.splashcompose.screen.random.RandomPhotoScreen
import com.hyejeanmoon.splashcompose.screen.settings.SettingsScreen
import com.hyejeanmoon.splashcompose.screen.webview.WebViewScreen
import com.hyejeanmoon.splashcompose.ui.theme.MoonGray
import com.hyejeanmoon.splashcompose.utils.NavUtils.back

@Composable
fun AppScaffold() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var shouldShowBackIcon by remember {
        mutableStateOf(false)
    }

    var shouldShowAppBar by remember {
        mutableStateOf(true)
    }

    val applicationName = "Moonlight Pictures"
    var appBarTitle by remember {
        mutableStateOf(applicationName)
    }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            BottomNavigation {
                val screenList = listOf(
                    Screen.Random,
                    Screen.Photos,
                    Screen.Collections,
                    Screen.Settings,
                )
                screenList.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = screen.drawableId),
                                contentDescription = stringResource(
                                    id = screen.stringId
                                ),
                                tint = if (currentRoute == screen.route) Color.Red else Color.Black
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = screen.stringId),
                                color = if (currentRoute == screen.route) Color.Red else Color.Black
                            )
                        }
                    )
                }
            }
        },
        topBar = {
            if (shouldShowAppBar) {
                if (shouldShowBackIcon) {
                    TopAppBar(
                        title = {
                            Text(
                                text = appBarTitle,
                                color = Color.Black
                            )
                        },
                        navigationIcon = {
                            Icon(
                                modifier = Modifier.clickable { navController.back() },
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back icon",
                                tint = Color.Black
                            )
                        }
                    )
                } else {
                    TopAppBar(
                        title = {
                            Text(
                                text = appBarTitle,
                                color = Color.Black
                            )
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            navController, startDestination = Screen.Random.route
        ) {
            composable(Screen.Random.route) {
                RandomPhotoScreen()
                shouldShowAppBar = true
                shouldShowBackIcon = false
                appBarTitle = applicationName
            }
            composable(Screen.Photos.route) {
                PhotoScreen()
                shouldShowAppBar = true
                shouldShowBackIcon = false
                appBarTitle = applicationName
            }
            composable(Screen.Collections.route) {
                CollectionsScreen()
                shouldShowAppBar = true
                shouldShowBackIcon = false
                appBarTitle = applicationName
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController = navController)
                shouldShowAppBar = true
                shouldShowBackIcon = false
                appBarTitle = applicationName
            }
            composable(Screen.License.route) {
                WebViewScreen(
                    urlToRender = "file:///android_asset/licenses.html"
                )
                shouldShowAppBar = true
                shouldShowBackIcon = true
                appBarTitle = context.getString(Screen.License.stringId)
            }
            composable(Screen.PhotoDetail.route) {
                shouldShowAppBar = false
                shouldShowBackIcon = false
                appBarTitle = applicationName
                appBarTitle = context.getString(Screen.PhotoDetail.stringId)
            }
            composable(Screen.UserDetail.route) {
                shouldShowAppBar = true
                shouldShowBackIcon = true
                appBarTitle = context.getString(Screen.UserDetail.stringId)
            }
            composable(Screen.Favorites.route) {
                shouldShowAppBar = true
                shouldShowBackIcon = true
                appBarTitle = context.getString(Screen.Favorites.stringId)
            }
        }
    }
}

@Composable
fun SetUpStatusBar() {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setStatusBarColor(
            color = MoonGray,
            darkIcons = useDarkIcons
        )
    }
}

@Composable
fun ErrorAlert(
    showAlert: Boolean = true
) {
    var openAlert by remember {
        mutableStateOf(showAlert)
    }
    if (openAlert) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Text(
                    modifier = Modifier.clickable { openAlert = false },
                    text = stringResource(id = R.string.alert_error_button)
                )
            },
            title = { Text(text = stringResource(id = R.string.alert_error_title)) },
            text = { Text(text = stringResource(id = R.string.alert_error_message)) }
        )
    }
}