package com.hyejeanmoon.splashcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hyejeanmoon.splashcompose.ui.theme.MoonGray
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.collectionphotos.PhotosOfCollectionActivity
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsScreen
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsViewModel
import com.hyejeanmoon.splashcompose.screen.favorite.LoveScreen
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailActivity
import com.hyejeanmoon.splashcompose.screen.photos.PhotoScreen
import com.hyejeanmoon.splashcompose.screen.photos.PhotosViewModel
import com.hyejeanmoon.splashcompose.screen.settings.SettingsItem
import com.hyejeanmoon.splashcompose.screen.settings.SettingsScreen
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val photosViewModel: PhotosViewModel by viewModels()
    private val collectionsViewModel: CollectionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SetUpStatusBar()

            SplashComposeApp(
                photosViewModel,
                collectionsViewModel,
                onCollectionsItemClick = { collectionId, collectionTitle ->
                    PhotosOfCollectionActivity.start(this, collectionId, collectionTitle)
                },
                onSettingsItemClick = { detail ->

                },
                settingsItems = listOf(
                    SettingsItem(
                        "Account",
                        listOf("User Account", "Log Out")
                    ),
                    SettingsItem(
                        "Application Settings",
                        listOf(
                            "Change Display Resolution",
                            "Change Download Resolution",
                            "Change Photo Display Order"
                        )
                    ),
                    SettingsItem(
                        "Others",
                        listOf("Version", "About Me")
                    )
                ),
                onPhotoClick = {
                    PhotoDetailActivity.start(it?.id.orEmpty(), this)
                }
            )
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

@Composable
fun SplashComposeApp(
    photoViewModel: PhotosViewModel,
    collectionsViewModel: CollectionsViewModel,
    onCollectionsItemClick: (String, String) -> Unit,
    onSettingsItemClick: (String) -> Unit,
    onPhotoClick: (Photo?) -> Unit,
    settingsItems: List<SettingsItem>
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
                        title = { Text(text = "Splash Compose", color = Color.Black) }
                    )
                }
            ) {
                NavHost(navController, startDestination = Screen.Photo.route) {
                    composable(Screen.Photo.route) {
                        PhotoScreen(
                            viewModel = photoViewModel,
                            onPhotoClick = onPhotoClick
                        )
                    }
                    composable(Screen.Collections.route) {
                        CollectionsScreen(
                            collectionsViewModel = collectionsViewModel,
                            onCollectionsItemClick = onCollectionsItemClick
                        )
                    }
                    composable(Screen.Love.route) { LoveScreen() }
                    composable(Screen.Settings.route) {
                        SettingsScreen(
                            settingsItems = settingsItems,
                            onSettingsItemClick = onSettingsItemClick
                        )
                    }
                }
            }
        }
    }
}
