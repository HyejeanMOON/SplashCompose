package com.hyejeanmoon.splashcompose.screen.collections

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.entity.Collections

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

@Composable
fun CollectionsImage(
    modifier: Modifier = Modifier,
    coverUrl: String
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 1.dp),
        painter = rememberGlidePainter(
            coverUrl,
            fadeIn = true,
            requestBuilder = {
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            }
        ),
        contentDescription = "collections cover image",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun CollectionsDetailText(
    modifier: Modifier = Modifier,
    collections: Collections
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(50.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(40.dp, 0.dp)
                .clip(CircleShape),
            painter = rememberGlidePainter(
                request = collections.user?.profileImage?.large,
                fadeIn = true,
                requestBuilder = {
                    diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                }
            ),
            contentDescription = "author image"
        )

        Text(
            modifier = Modifier.padding(40.dp, 0.dp),
            text = collections.title.orEmpty(),
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.End
        )

        Text(
            modifier = Modifier.padding(40.dp, 0.dp),
            text = "${collections.totalPhotos} photos",
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.End
        )
    }
}