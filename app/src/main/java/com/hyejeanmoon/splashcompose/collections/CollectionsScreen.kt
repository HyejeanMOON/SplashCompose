package com.hyejeanmoon.splashcompose.collections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hyejeanmoon.splashcompose.compose.CollectionsImage
import com.hyejeanmoon.splashcompose.compose.CollectionsNumbersText
import com.hyejeanmoon.splashcompose.compose.CollectionsTitleText
import com.hyejeanmoon.splashcompose.compose.PhotoImage
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.ui.theme.Transparent_Gray

@Composable
fun CollectionsScreen(
    modifier: Modifier = Modifier,
    collectionsViewModel: CollectionsViewModel,
    onCollectionsItemClick:(String) -> Unit
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
                    onClick = { id->
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
    onClick:(String) -> Unit
) {
    Box(modifier = modifier.clickable {
        onClick(collections.id.orEmpty())
    }) {
//        val (img, title, numbers) = createRefs()

        CollectionsImage(
//            modifier = Modifier.constrainAs(img) {
//                top.linkTo(parent.top)
//                bottom.linkTo(parent.bottom)
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//            },
            coverUrl = collections.coverPhoto?.urls?.small.orEmpty()
        )
//
//        CollectionsTitleText(
//            modifier = Modifier.constrainAs(title) {
//                top.linkTo(img.top)
//                bottom.linkTo(img.bottom)
//                start.linkTo(img.start)
//                end.linkTo(img.end)
//            },
//            collectionsName = collections.title.orEmpty()
//        )
//
//        CollectionsNumbersText(
//            modifier = Modifier
//                .constrainAs(numbers) {
//                    bottom.linkTo(img.bottom)
//                    end.linkTo(img.end)
//                }
//                .padding(0.dp, 0.dp, 20.dp, 20.dp),
//            numberOfCollections = collections.totalPhotos.orEmpty()
//        )
    }
}