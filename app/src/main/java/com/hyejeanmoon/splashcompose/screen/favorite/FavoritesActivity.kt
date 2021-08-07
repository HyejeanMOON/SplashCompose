package com.hyejeanmoon.splashcompose.screen.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailActivity
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesActivity : ComponentActivity() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SetUpStatusBar()

            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                navigationIcon = {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            finish()
                                        },
                                        painter = painterResource(id = R.drawable.ic_arrow_back),
                                        contentDescription = "back icon",
                                        tint = Color.Black
                                    )
                                },
                                title = {
                                    Text(
                                        text = getString(R.string.title_favorites),
                                        color = Color.Black
                                    )
                                }
                            )
                        }
                    ) {
                        FavoriteScreen(
                            viewModel = favoriteViewModel,
                            onFavoritePhotoClick = { photoId ->
                                PhotoDetailActivity.start(photoId, this)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        favoriteViewModel.getAllFavoritePhotos()
    }

    companion object {
        fun start(activity: ComponentActivity) {
            val intent = Intent()
            intent.setClass(activity, FavoritesActivity::class.java)
            activity.startActivity(intent)
        }
    }
}