package com.hyejeanmoon.splashcompose.screen.photos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.rememberCoilPainter
import com.hyejeanmoon.splashcompose.entity.Photo

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel,
    onPhotoClick: (Photo?) -> Unit
) {
    val pagingItems = viewModel.photoList.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(lazyPagingItems = pagingItems) { photoItem ->
            val item by remember {
                mutableStateOf(photoItem)
            }
            PhotoImage(
                photo = item,
                onPhotoClick = onPhotoClick
            )
        }
    }

    pagingItems.apply {
        when {
            loadState.refresh is LoadState.Error -> {

            }
            loadState.append is LoadState.Error -> {

            }
        }
    }
}

@Composable
fun PhotoImage(
    modifier: Modifier = Modifier,
    photo: Photo?,
    onPhotoClick: (Photo?) -> Unit
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 1.dp)
            .clickable { onPhotoClick(photo) },
        painter = rememberCoilPainter(
            photo?.urls?.regular.orEmpty(),
            fadeIn = true
        ),
        contentDescription = "photo image",
        contentScale = ContentScale.FillWidth
    )
}