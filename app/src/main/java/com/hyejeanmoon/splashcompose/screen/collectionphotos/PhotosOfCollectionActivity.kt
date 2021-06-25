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
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailActivity
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme

class PhotosOfCollectionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val collectionTitle = intent.getStringExtra(COLLECTION_TITLE).orEmpty()

        val viewModel: PhotosOfCollectionViewModel by viewModels()

        setContent {
            SetUpStatusBar()

            PhotosOfCollectionsApp(
                viewModel = viewModel,
                onPhotoClick = {
                    PhotoDetailActivity.start(it?.id.orEmpty(), this)
                },
                onBackIconClick = { finish() },
                collectionTitle = collectionTitle
            )
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

@Composable
fun PhotosOfCollectionsApp(
    viewModel: PhotosOfCollectionViewModel,
    onPhotoClick: (Photo?) -> Unit,
    onBackIconClick: () -> Unit,
    collectionTitle: String
) {
    SplashComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            PhotosOfCollectionScreen(
                photosOfCollectionViewModel = viewModel,
                onPhotoClick = onPhotoClick,
                onBackIconClick = onBackIconClick,
                collectionTitle = collectionTitle
            )
        }
    }
}