package com.hyejeanmoon.splashcompose.screen.collectionphotos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailActivity
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme

class PhotosOfCollectionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusBarColor(R.color.gray, true)

        val viewModel: PhotosOfCollectionViewModel by viewModels()

        setContent {
            PhotosOfCollectionsApp(
                viewModel = viewModel,
                onPhotoClick = {
                    PhotoDetailActivity.start(it?.id.orEmpty(), this)
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
        const val INTENT_ID = "INTENT_ID"

        fun start(activity: ComponentActivity, photoId: String) {
            val intent = Intent()
            intent.setClass(activity, PhotosOfCollectionActivity::class.java)
            intent.putExtra(INTENT_ID, photoId)
            activity.startActivity(intent)
        }
    }
}

@Composable
fun PhotosOfCollectionsApp(
    viewModel: PhotosOfCollectionViewModel,
    onPhotoClick: (Photo?) -> Unit
) {
    SplashComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            PhotosOfCollectionScreen(
                photosOfCollectionViewModel = viewModel,
                onPhotoClick = onPhotoClick
            )
        }
    }
}