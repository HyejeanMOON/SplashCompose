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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.collectionphotos.PhotosOfCollectionActivity
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsScreen
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsViewModel
import com.hyejeanmoon.splashcompose.screen.favorite.FavoriteScreen
import com.hyejeanmoon.splashcompose.screen.favorite.FavoriteViewModel
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailActivity
import com.hyejeanmoon.splashcompose.screen.photos.PhotoScreen
import com.hyejeanmoon.splashcompose.screen.photos.PhotosViewModel
import com.hyejeanmoon.splashcompose.screen.settings.SettingItemDetail
import com.hyejeanmoon.splashcompose.screen.settings.SettingsItem
import com.hyejeanmoon.splashcompose.screen.settings.SettingsScreen
import com.hyejeanmoon.splashcompose.screen.settings.SettingsViewModel
import com.hyejeanmoon.splashcompose.screen.webview.WebViewActivity
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import com.hyejeanmoon.splashcompose.utils.DataManager
import com.hyejeanmoon.splashcompose.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val photosViewModel: PhotosViewModel by viewModels()
    private val collectionsViewModel: CollectionsViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPref()

        setContent {

            SetUpStatusBar()

            SplashComposeApp(
                photosViewModel,
                collectionsViewModel,
                onCollectionsItemClick = { collectionId, collectionTitle ->
                    PhotosOfCollectionActivity.start(this, collectionId, collectionTitle)
                },
                onSettingsItemClick = { detail ->
                    when (detail) {
                        SETTINGS_ITEM_ABOUT_DEVELOPER -> {
                            val uri = Uri.parse("https://github.com/HyejeanMOON")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }

                        SETTINGS_ITEM_VERSION -> {
                            // do nothing
                        }

                        SETTINGS_ITEM_LICENSES -> {
                            WebViewActivity.start(
                                "file:///android_asset/licenses.html",
                                SETTINGS_ITEM_LICENSES,
                                this
                            )
                        }

                        SETTINGS_ITEM_CLEAR_CACHE -> {
                            DataManager.clearCacheInScopedStorage(this)
                        }
                    }
                },
                settingsItems = listOf(
                    SettingsItem(
                        SETTINGS_TITLE_APPLICATION_SETTINGS,
                        listOf(
                            SettingItemDetail(
                                title = SETTINGS_ITEM_DISPLAY_RESOLUTION
                            ),
                            SettingItemDetail(
                                title = SETTINGS_ITEM_PHOTO_DISPLAY_ORDER,
                                "This setting will take effect the next time you start the app"
                            ),
                            SettingItemDetail(title = SETTINGS_ITEM_CLEAR_CACHE)
                        )
                    ),
                    SettingsItem(
                        SETTINGS_TITLE_OTHERS,
                        listOf(
                            SettingItemDetail(
                                title = SETTINGS_ITEM_VERSION,
                                content = BuildConfig.VERSION_NAME
                            ),
                            SettingItemDetail(
                                title = SETTINGS_ITEM_LICENSES
                            ),
                            SettingItemDetail(title = SETTINGS_ITEM_ABOUT_DEVELOPER)
                        )
                    )
                ),
                onPhotoClick = {
                    PhotoDetailActivity.start(it?.id.orEmpty(), this)
                },
                favoriteViewModel = favoriteViewModel,
                onFavoritePhotoClick = { photoId ->
                    PhotoDetailActivity.start(
                        photoId,
                        this
                    )
                },
                settingsViewModel = settingsViewModel
            )
        }
    }

    private fun initPref() {
        val pref = SharedPreferencesUtils(this)

        //  download resolution
        val downloadResolution = pref.getString(SharedPreferencesUtils.KEY_DOWNLOAD_RESOLUTION)
        if (downloadResolution.isBlank()) {
            pref.putString(
                SharedPreferencesUtils.KEY_DOWNLOAD_RESOLUTION,
                Resolution.FULL.name
            )
        }

        //  display resolution
        val displayResolution = pref.getString(SharedPreferencesUtils.KEY_DISPLAY_RESOLUTION)
        if (displayResolution.isBlank()) {
            pref.putString(
                SharedPreferencesUtils.KEY_DISPLAY_RESOLUTION,
                Resolution.REGULAR.name
            )
        }
        //  order by
        val orderBy = pref.getString(SharedPreferencesUtils.KEY_ORDER_BY)
        if (orderBy.isBlank()) {
            pref.putString(
                SharedPreferencesUtils.KEY_ORDER_BY,
                OrderBy.LATEST.name
            )
        }
    }

    override fun onResume() {
        super.onResume()

        favoriteViewModel.getAllFavoritePhotos()
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        const val SETTINGS_ITEM_ABOUT_DEVELOPER = "About Developer"
        const val SETTINGS_ITEM_VERSION = "Version"
        const val SETTINGS_ITEM_CLEAR_CACHE = "Clear Cache"
        const val SETTINGS_ITEM_PHOTO_DISPLAY_ORDER = "Photo Display Order"
        const val SETTINGS_ITEM_DISPLAY_RESOLUTION = "Display Resolution"
        const val SETTINGS_ITEM_LICENSES = "Licenses"
        const val SETTINGS_TITLE_OTHERS = "Others"
        const val SETTINGS_TITLE_APPLICATION_SETTINGS = "Application Settings"
        const val APPLICATION_TITLE = "Splash Wallpaper"
    }
}

@ExperimentalFoundationApi
@Composable
fun SplashComposeApp(
    photoViewModel: PhotosViewModel,
    collectionsViewModel: CollectionsViewModel,
    favoriteViewModel: FavoriteViewModel,
    settingsViewModel: SettingsViewModel,
    onCollectionsItemClick: (String, String) -> Unit,
    onSettingsItemClick: (String) -> Unit,
    onPhotoClick: (Photo?) -> Unit,
    onFavoritePhotoClick: (String) -> Unit,
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
                        title = { Text(text = MainActivity.APPLICATION_TITLE, color = Color.Black) }
                    )
                }
            ) {

                var openDialog = remember { mutableStateOf(false) }
                var settingsItem = remember { mutableStateOf("") }

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
                    composable(Screen.Love.route) {
                        FavoriteScreen(
                            viewModel = favoriteViewModel,
                            onFavoritePhotoClick = onFavoritePhotoClick
                        )
                    }
                    composable(Screen.Settings.route) {
                        SettingsScreen(
                            settingsItems = settingsItems,
                            onSettingsItemClick = { title ->
                                when (title) {
                                    MainActivity.SETTINGS_ITEM_DISPLAY_RESOLUTION,
                                    MainActivity.SETTINGS_ITEM_PHOTO_DISPLAY_ORDER -> {
                                        openDialog.value = true
                                        settingsItem.value = title
                                    }
                                    MainActivity.SETTINGS_ITEM_ABOUT_DEVELOPER,
                                    MainActivity.SETTINGS_ITEM_VERSION,
                                    MainActivity.SETTINGS_ITEM_CLEAR_CACHE,
                                    MainActivity.SETTINGS_ITEM_LICENSES -> {
                                        onSettingsItemClick(title)
                                    }
                                }
                            }
                        )
                    }
                }
                if (openDialog.value) {
                    SettingsAlertDialog(
                        item = settingsItem.value,
                        onCloseDialog = {
                            openDialog.value = false
                        },
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsAlertDialog(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel,
    item: String,
    onCloseDialog: () -> Unit
) {
    AlertDialog(
        modifier = modifier.padding(40.dp, 0.dp),
        onDismissRequest = {
            onCloseDialog()
        },
        confirmButton = {
            Button(
                onClick = {
                    onCloseDialog()
                }
            ) {
                Text(
                    text = "Dismiss",
                    color = Color.Black,
                )
            }
        },
        title = {
            Text(
                text = item,
                color = Color.Black
            )
        },
        text = {
            RadioButtonList(
                settingsViewModel = settingsViewModel,
                item = item
            )
        }
    )
}

@Composable
fun RadioButtonList(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel,
    item: String
) {
    var radioOptionList: List<String> = listOf()
    var initialPosition = 0

    when (item) {
        MainActivity.SETTINGS_ITEM_PHOTO_DISPLAY_ORDER -> {
            radioOptionList = settingsViewModel.orderByList
            initialPosition = settingsViewModel.getOrderByPosition()
        }
        MainActivity.SETTINGS_ITEM_DISPLAY_RESOLUTION -> {
            radioOptionList = settingsViewModel.displayResolutionList
            initialPosition = settingsViewModel.getDisplayResolutionPosition()
        }
    }

    var selectedOption = remember { mutableStateOf(initialPosition) }
    Column(
        modifier = modifier
    ) {
        radioOptionList.forEachIndexed { index, text ->
            Row(
                Modifier
                    .selectable(
                        selected = (text == radioOptionList[selectedOption.value]),
                        onClick = {
                            selectedOption.value = index
                            when (item) {
                                MainActivity.SETTINGS_ITEM_PHOTO_DISPLAY_ORDER -> {
                                    settingsViewModel.putOrderBy(text)
                                }
                                MainActivity.SETTINGS_ITEM_DISPLAY_RESOLUTION -> {
                                    settingsViewModel.putDisplayResolution(text)
                                }
                            }
                        }
                    )
                    .padding(horizontal = 20.dp)
            ) {
                RadioButton(
                    selected = (text == radioOptionList[selectedOption.value]),
                    onClick = {
                        selectedOption.value = index
                        when (item) {
                            MainActivity.SETTINGS_ITEM_PHOTO_DISPLAY_ORDER -> {
                                settingsViewModel.putOrderBy(text)
                            }
                            MainActivity.SETTINGS_ITEM_DISPLAY_RESOLUTION -> {
                                settingsViewModel.putDisplayResolution(text)
                            }
                        }
                    }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}