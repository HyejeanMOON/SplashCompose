package com.hyejeanmoon.splashcompose.screen.collections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hyejeanmoon.splashcompose.compose.*
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