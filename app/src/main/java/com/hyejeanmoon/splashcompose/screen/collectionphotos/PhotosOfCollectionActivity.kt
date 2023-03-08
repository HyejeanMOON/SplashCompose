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

package com.hyejeanmoon.splashcompose.screen.collectionphotos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.hyejeanmoon.splashcompose.EnvParameters
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.hilt.SystemComponentsModule
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import com.hyejeanmoon.splashcompose.utils.putString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosOfCollectionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val collectionTitle = intent.getStringExtra(COLLECTION_TITLE).orEmpty()

        setContent {
            SetUpStatusBar()

            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PhotosOfCollectionScreen(
                        onBackIconClick = { finish() },
                        collectionTitle = collectionTitle
                    )
                }
            }
        }
    }

    companion object {
        const val COLLECTION_ID = "COLLECTION_ID"
        private const val COLLECTION_TITLE = "COLLECTION_TITLE"

        fun start(context: Context, collectionId: String, collectionTitle: String) {
            val sharedPreferences = SystemComponentsModule().provideSharedPreferences(context)
            sharedPreferences.putString(EnvParameters.KEY_COLLECTION_ID,collectionId)
            val intent = Intent()
            intent.setClass(context, PhotosOfCollectionActivity::class.java)
            intent.putExtra(COLLECTION_TITLE, collectionTitle)
            context.startActivity(intent)
        }
    }
}