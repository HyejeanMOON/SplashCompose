package com.hyejeanmoon.splashcompose.screen.userdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.content.ContextCompat
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme

class UserDetailActivity : ComponentActivity() {

    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getUserDetailInfo()
        viewModel.getUsersPhotos()

        setContent {
            SetUpStatusBar()

            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    UserDetailScreen(
                        viewModel = viewModel,
                        onBackIconClick = { finish() }
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