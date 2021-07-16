package com.hyejeanmoon.splashcompose.screen.photodetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailActivity

class PhotoDetailActivity : ComponentActivity() {

    private val viewModel: PhotoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SetUpStatusBar()

            PhotoDetailScreen(
                viewModel = viewModel,
                onBackIconClick = {
                    finish()
                },
                onUserInfoClick = { userName ->
                    UserDetailActivity.startUserDetailActivity(this, userName = userName)
                }
            )
        }
    }

    companion object {
        const val INTENT_PHOTO_ID = "INTENT_PHOTO_ID"

        fun start(photoId: String, activity: ComponentActivity) {
            val intent = Intent()
            intent.setClass(activity, PhotoDetailActivity::class.java)
            intent.putExtra(INTENT_PHOTO_ID, photoId)
            activity.startActivity(intent)
        }
    }
}