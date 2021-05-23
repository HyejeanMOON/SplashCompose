package com.hyejeanmoon.splashcompose.photo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hyejeanmoon.splashcompose.compose.PhotoImage
import com.hyejeanmoon.splashcompose.photo.PhotoViewModel

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotoViewModel
) {
    val pagingItems = viewModel.photoList.collectAsLazyPagingItems()
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(lazyPagingItems = pagingItems) { item ->
            PhotoImage(photoUrl = item?.urls?.regular ?: "", photoName = item?.user?.name ?: "")
        }
    }
}