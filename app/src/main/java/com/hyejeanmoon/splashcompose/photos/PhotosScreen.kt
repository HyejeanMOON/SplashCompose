package com.hyejeanmoon.splashcompose.photos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hyejeanmoon.splashcompose.compose.PhotoImage

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel
) {
    val pagingItems = viewModel.photoList.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(lazyPagingItems = pagingItems) { photoItem ->
            val item by remember {
                mutableStateOf(photoItem)
            }
            PhotoImage(photoUrl = item?.urls?.regular.orEmpty())
        }
    }
}