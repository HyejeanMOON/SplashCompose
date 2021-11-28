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

package com.hyejeanmoon.splashcompose.screen.favorites

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesActivity : ComponentActivity() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SetUpStatusBar()

            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                navigationIcon = {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            finish()
                                        },
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "back icon",
                                        tint = Color.Black
                                    )
                                },
                                title = {
                                    Text(
                                        text = getString(R.string.screen_favorites),
                                        color = Color.Black
                                    )
                                }
                            )
                        }
                    ) {
                        FavoritesScreen()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        favoritesViewModel.getAllFavoritePhotos()
    }

    companion object {
        fun start(activity: ComponentActivity) {
            val intent = Intent()
            intent.setClass(activity, FavoritesActivity::class.java)
            activity.startActivity(intent)
        }
    }
}