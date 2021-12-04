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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsScreen
import com.hyejeanmoon.splashcompose.screen.photos.PhotoScreen
import com.hyejeanmoon.splashcompose.screen.random.RandomPhotoScreen
import com.hyejeanmoon.splashcompose.screen.settings.SettingsScreen
import com.hyejeanmoon.splashcompose.screen.webview.WebViewScreen
import com.hyejeanmoon.splashcompose.utils.NavUtils.back

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppScaffold() {

    val navController = rememberNavController().apply {
        navigatorProvider += remember(this) { AnimatedComposeNavigator() }
    }
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
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
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
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.Random.route
        ) {
            animatedComposable(Screen.Random.route, this, navBackStackEntry) {
                RandomPhotoScreen()
                shouldShowAppBar = true
                shouldShowBackIcon = false
                appBarTitle = applicationName
            }
            animatedComposable(Screen.Photos.route, this, navBackStackEntry) {
                PhotoScreen()
                shouldShowAppBar = true
                shouldShowBackIcon = false
                appBarTitle = applicationName
            }
            animatedComposable(Screen.Collections.route, this, navBackStackEntry) {
                CollectionsScreen()
                shouldShowAppBar = true
                shouldShowBackIcon = false
                appBarTitle = applicationName
            }
            animatedComposable(Screen.Settings.route, this, navBackStackEntry) {
                SettingsScreen(navController = navController)
                shouldShowAppBar = true
                shouldShowBackIcon = false
                appBarTitle = applicationName
            }
            animatedComposable(Screen.License.route, this, navBackStackEntry) {
                WebViewScreen(
                    urlToRender = "file:///android_asset/licenses.html"
                )
                shouldShowAppBar = true
                shouldShowBackIcon = true
                appBarTitle = context.getString(Screen.License.stringId)
            }
            animatedComposable(Screen.PhotoDetail.route, this, navBackStackEntry) {
                shouldShowAppBar = false
                shouldShowBackIcon = false
                appBarTitle = applicationName
                appBarTitle = context.getString(Screen.PhotoDetail.stringId)
            }
            animatedComposable(Screen.UserDetail.route, this, navBackStackEntry) {
                shouldShowAppBar = true
                shouldShowBackIcon = true
                appBarTitle = context.getString(Screen.UserDetail.stringId)
            }
            animatedComposable(Screen.Favorites.route, this, navBackStackEntry) {
                shouldShowAppBar = true
                shouldShowBackIcon = true
                appBarTitle = context.getString(Screen.Favorites.stringId)
            }

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun animatedComposable(
    route: String,
    navGraphBuilder: NavGraphBuilder,
    navBackStackEntry: NavBackStackEntry?,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    navGraphBuilder.composable(
        route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(200))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(200))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(200))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(200))
        }
    ) {
        navBackStackEntry?.also {
            content(it)
        }
    }
}
