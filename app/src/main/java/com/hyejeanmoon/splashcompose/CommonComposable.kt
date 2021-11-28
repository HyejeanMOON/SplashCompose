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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.hyejeanmoon.splashcompose.ui.theme.MoonGray

@Composable
fun AppScaffold() {
    val applicationTitle = "Moonlight Pictures"

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scaffoldState = rememberScaffoldState()

    var expanded by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

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
            TopAppBar(
                title = {
                    Text(
                        text = applicationTitle,
                        color = Color.Black
                    )
                }
            )
        }
    ) {

        var openDialog by remember { mutableStateOf(false) }
        var settingsItem by remember { mutableStateOf("") }

        NavHost(
            navController, startDestination = Screen.Random.route
        ) {
            composable(Screen.Random.route) {
                RandomPhotoScreen()
            }
            composable(Screen.Photos.route) {
                PhotoScreen()
            }
            composable(Screen.Collections.route) {
                CollectionsScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
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