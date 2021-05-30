package com.hyejeanmoon.splashcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hyejeanmoon.splashcompose.collectionphotos.PhotosOfCollectionActivity
import com.hyejeanmoon.splashcompose.collections.CollectionsScreen
import com.hyejeanmoon.splashcompose.collections.CollectionsViewModel
import com.hyejeanmoon.splashcompose.love.LoveScreen
import com.hyejeanmoon.splashcompose.photo.PhotoScreen
import com.hyejeanmoon.splashcompose.photo.PhotoViewModel
import com.hyejeanmoon.splashcompose.settings.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val photoViewModel: PhotoViewModel by viewModels()
    private val collectionsViewModel: CollectionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColor(R.color.gray, true)

        setContent {
            SplashComposeApp(
                photoViewModel,
                collectionsViewModel,
                onCollectionsItemClick = {
                    val intent = Intent()
                    intent.setClass(this, PhotosOfCollectionActivity::class.java)
                    intent.putExtra(PhotosOfCollectionActivity.INTENT_ID, it)
                    startActivity(intent)
                }
            )
        }
    }

    /**
     * Set color of statusBar
     */
    private fun setStatusBarColor(@ColorRes color: Int, isLightStatusBar: Boolean) {
        window.statusBarColor = ContextCompat.getColor(this, color)
        if (isLightStatusBar) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = 0
        }
    }
}

@Composable
fun SplashComposeApp(
    photoViewModel: PhotoViewModel,
    collectionsViewModel: CollectionsViewModel,
    onCollectionsItemClick: (String) -> Unit
) {
    SplashComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier,
                bottomBar = {
                    BottomNavigation {
                        val screenList = listOf(
                            Screen.Photo,
                            Screen.Collections,
                            Screen.Love,
                            Screen.Settings,
                        )

                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route

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
                        title = { Text(text = "SplashCompose", color = Color.Black) }
                    )
                }
            ) {
                NavHost(navController, startDestination = Screen.Photo.route) {
                    composable(Screen.Photo.route) {
                        PhotoScreen(viewModel = photoViewModel)
                    }
                    composable(Screen.Collections.route) {
                        CollectionsScreen(
                            collectionsViewModel = collectionsViewModel,
                            onCollectionsItemClick = onCollectionsItemClick
                        )
                    }
                    composable(Screen.Love.route) { LoveScreen() }
                    composable(Screen.Settings.route) { SettingsScreen() }
                }
            }
        }
    }
}
