package com.hyejeanmoon.splashcompose.screen.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hyejeanmoon.splashcompose.db.FavoritePhoto

@ExperimentalFoundationApi
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel,
    onFavoritePhotoClick: (String) -> Unit
) {

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val favoritePhotoList by viewModel.favoritePhotoList.observeAsState()

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.getAllFavoritePhotos() }
    ) {
        LazyVerticalGrid(
            modifier = modifier.fillMaxSize().padding(0.dp,0.dp,0.dp,48.dp),
            cells = GridCells.Adaptive(minSize = 160.dp)
        ) {
            favoritePhotoList?.also {
                items(it) { photo ->
                    FavoritePhotoItem(
                        photo = photo,
                        onFavoritePhotoClick = onFavoritePhotoClick
                    )
                }
            }
        }
    }
}

@Composable
fun FavoritePhotoItem(
    modifier: Modifier = Modifier,
    photo: FavoritePhoto,
    onFavoritePhotoClick: (String) -> Unit
) {
    Image(
        modifier = modifier
            .size(160.dp)
            .clickable {
                onFavoritePhotoClick(photo.id)
            },
        painter = rememberGlidePainter(
            request = photo.photoUrl.orEmpty(),
            fadeIn = true,
            requestBuilder = {
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            }
        ),
        contentDescription = "favorite photo",
        contentScale = ContentScale.Crop
    )
}