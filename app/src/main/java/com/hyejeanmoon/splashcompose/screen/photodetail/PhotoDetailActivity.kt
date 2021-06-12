package com.hyejeanmoon.splashcompose.screen.photodetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.ContextCompat
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.Photo

class PhotoDetailActivity : ComponentActivity() {

    private val viewModel: PhotoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColor(R.color.gray, true)
        viewModel.getPhotoById()

        setContent {
            PhotoDetailScreen(
                viewModel = viewModel,
                onDownloadClick = {

                }
            )
        }
    }


    /**
     * Set color of statusBar
     */
    private fun setStatusBarColor(@ColorRes color: Int, isLightStatusBar: Boolean) {
        window.statusBarColor = ContextCompat.getColor(this, color)
        if (isLightStatusBar) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = 0
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