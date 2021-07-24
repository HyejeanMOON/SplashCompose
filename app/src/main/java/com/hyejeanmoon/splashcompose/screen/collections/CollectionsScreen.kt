package com.hyejeanmoon.splashcompose.screen.collections

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.rememberCoilPainter
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.utils.PhotoUtils

@Composable
fun CollectionsScreen(
    modifier: Modifier = Modifier,
    collectionsViewModel: CollectionsViewModel,
    onCollectionsItemClick: (String, String) -> Unit
) {
    val pagingItems = collectionsViewModel.collections.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(pagingItems) { collectionsItem ->
            collectionsItem?.also {
                val item by remember { mutableStateOf(collectionsItem) }
                CollectionsItem(
                    collections = item,
                    onCollectionsItemClick = onCollectionsItemClick,
                    resolution = collectionsViewModel.resolution
                )
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

@Composable
fun CollectionsItem(
    modifier: Modifier = Modifier,
    resolution:String,
    collections: Collections,
    onCollectionsItemClick: (String, String) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .padding(0.dp, 1.dp)
            .fillMaxWidth()
            .requiredHeight(300.dp)
            .clickable {
                onCollectionsItemClick(collections.id.orEmpty(), collections.title.orEmpty())
            }
    ) {

        val coverPhotoUrl = PhotoUtils.getCoverPhotoOfCollectionUrlByResolution(
            resolution,
            collections
        )

        val (image, title, photoNumber) = createRefs()

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
                .constrainAs(image) {
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                    top.linkTo(image.top)
                    bottom.linkTo(image.bottom)
                },
            painter = rememberCoilPainter(
                coverPhotoUrl,
                fadeIn = true
            ),
            contentDescription = "collections cover image",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                    top.linkTo(image.top)
                    bottom.linkTo(image.bottom)
                },
            text = collections.title.orEmpty(),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .constrainAs(photoNumber) {
                    end.linkTo(image.end)
                    bottom.linkTo(image.bottom)
                }
                .padding(20.dp),
            text = "${collections.totalPhotos.orEmpty()} photos",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}