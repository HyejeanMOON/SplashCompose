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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.hyejeanmoon.splashcompose.hilt.SystemComponentsModule
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import com.hyejeanmoon.splashcompose.utils.getString
import com.hyejeanmoon.splashcompose.utils.putString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPref()

        onBackPressedDispatcher.addCallback {
            viewModel.shouldShowExitAlertDialog.postValue(true)
        }

        setContent {
            SetUpStatusBar()

            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppScaffold()
                    ExitAlertDialog(viewModel) {
                        finish()
                    }
                }
            }
        }
    }

    private fun initPref() {
        val pref = SystemComponentsModule().provideSharedPreferences(this)

        //  display resolution
        val displayResolution = pref.getString(EnvParameters.KEY_DISPLAY_RESOLUTION)
        if (displayResolution.isBlank()) {
            pref.putString(
                EnvParameters.KEY_DISPLAY_RESOLUTION,
                Resolution.REGULAR.name
            )
        }
        //  order by
        val orderBy = pref.getString(EnvParameters.KEY_ORDER_BY)
        if (orderBy.isBlank()) {
            pref.putString(
                EnvParameters.KEY_ORDER_BY,
                OrderBy.LATEST.name
            )
        }
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }
}