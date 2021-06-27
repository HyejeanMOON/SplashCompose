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
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme

class UserDetailActivity : ComponentActivity() {

    private val viewModel: UserDetailViewModel by viewModels()

    @ExperimentalAnimationApi
    @ExperimentalPagerApi
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