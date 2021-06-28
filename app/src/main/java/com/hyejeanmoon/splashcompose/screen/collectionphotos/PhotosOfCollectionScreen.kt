package com.hyejeanmoon.splashcompose.screen.collectionphotos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.photos.PhotoImage

@Composable
fun PhotosOfCollectionScreen(
    modifier: Modifier = Modifier,
    photosOfCollectionViewModel: PhotosOfCollectionViewModel,
    onPhotoClick: (Photo?) -> Unit,
    onBackIconClick: () -> Unit,
    collectionTitle: String
) {
    val pagingItems = photosOfCollectionViewModel.photosOfCollections.collectAsLazyPagingItems()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = collectionTitle,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable { onBackIconClick() },
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "back icon",
                        tint = Color.Black
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(lazyPagingItems = pagingItems) { photoItem ->
                val item by remember {
                    mutableStateOf(photoItem)
                }
                PhotoImage(photo = item, onPhotoClick = onPhotoClick)
            }
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