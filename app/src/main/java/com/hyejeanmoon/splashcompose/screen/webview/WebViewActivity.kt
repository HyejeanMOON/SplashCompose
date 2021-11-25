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

package com.hyejeanmoon.splashcompose.screen.webview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme

class WebViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra(WEB_VIEW_URL)
        val title = intent.getStringExtra(WEB_VIEW_TITLE)

        setContent {
            SetUpStatusBar()
            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = title.orEmpty(),
                                        color = Color.Black
                                    )
                                },
                                navigationIcon = {
                                    Icon(
                                        modifier = Modifier.clickable { finish() },
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "back icon",
                                        tint = Color.Black
                                    )
                                }
                            )
                        }
                    ) {
                        WebPageScreen(urlToRender = url.orEmpty())
                    }
                }
            }
        }
    }

    companion object {
        private const val WEB_VIEW_URL = "WEB_VIEW_URL"
        private const val WEB_VIEW_TITLE = "WEB_VIEW_TITLE"

        fun start(url: String, title: String, activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, WebViewActivity::class.java)
            intent.putExtra(WEB_VIEW_URL, url)
            intent.putExtra(WEB_VIEW_TITLE, title)
            activity.startActivity(intent)
        }
    }
}