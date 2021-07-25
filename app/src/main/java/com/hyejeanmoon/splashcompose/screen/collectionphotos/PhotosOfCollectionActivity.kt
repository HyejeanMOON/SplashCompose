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

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailActivity
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosOfCollectionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val collectionTitle = intent.getStringExtra(COLLECTION_TITLE).orEmpty()

        val viewModel: PhotosOfCollectionViewModel by viewModels()

        setContent {
            SetUpStatusBar()

            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PhotosOfCollectionScreen(
                        photosOfCollectionViewModel = viewModel,
                        onPhotoClick = { PhotoDetailActivity.start(it?.id.orEmpty(), this) },
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

        fun start(activity: ComponentActivity, collectionId: String, collectionTitle: String) {
            val intent = Intent()
            intent.setClass(activity, PhotosOfCollectionActivity::class.java)
            intent.putExtra(COLLECTION_ID, collectionId)
            intent.putExtra(COLLECTION_TITLE, collectionTitle)
            activity.startActivity(intent)
        }
    }
}