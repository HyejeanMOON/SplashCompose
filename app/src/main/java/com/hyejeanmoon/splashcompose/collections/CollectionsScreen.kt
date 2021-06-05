package com.hyejeanmoon.splashcompose.collections

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.compose.*
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.ui.theme.Transparent_Gray

@Composable
fun CollectionsScreen(
    modifier: Modifier = Modifier,
    collectionsViewModel: CollectionsViewModel,
    onCollectionsItemClick: (String) -> Unit
) {
    val pagingItems = collectionsViewModel.collections.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(lazyPagingItems = pagingItems) { collectionsItem ->
            collectionsItem?.also {
                val item by remember { mutableStateOf(collectionsItem) }
                CollectionsItem(
                    collections = item,
                    onClick = { id ->
                        onCollectionsItemClick(id)
                    }
                )
            }
        }
    }
}

@Composable
fun CollectionsItem(
    modifier: Modifier = Modifier,
    collections: Collections,
    onClick: (String) -> Unit
) {
    Column(modifier = modifier
        .clickable {
        onClick(collections.id.orEmpty())
    }) {
        CollectionsImage(
            coverUrl = collections.coverPhoto?.urls?.regular.orEmpty()
        )
//        CollectionsDetailText(collections = collections)
    }
}