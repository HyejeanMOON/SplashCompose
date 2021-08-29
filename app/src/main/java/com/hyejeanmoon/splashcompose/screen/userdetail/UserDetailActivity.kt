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

package com.hyejeanmoon.splashcompose.screen.userdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.accompanist.pager.ExperimentalPagerApi
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.screen.collectionphotos.PhotosOfCollectionActivity
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailActivity
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : ComponentActivity() {

    private val viewModel: UserDetailViewModel by viewModels()

    @ExperimentalPagerApi
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getUserDetailInfo()

        setContent {
            SetUpStatusBar()

            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    UserDetailScreen(
                        viewModel = viewModel,
                        onBackIconClick = { finish() },
                        onCollectionItemsClick = { collectionId, collectionTitle ->
                            PhotosOfCollectionActivity.start(this, collectionId, collectionTitle)
                        },
                        onPhotoClick = {
                            PhotoDetailActivity.start(it?.id.orEmpty(), this)
                        },
                        onUserInfoClick = { userName ->
                            UserDetailActivity.startUserDetailActivity(
                                this,
                                userName
                            )
                            finish()
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val INTENT_USER_NAME = "INTENT_USER_NAME"

        fun startUserDetailActivity(activity: Activity, userName: String) {
            val intent = Intent()
            intent.putExtra(INTENT_USER_NAME, userName)
            intent.setClass(activity, UserDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }
}