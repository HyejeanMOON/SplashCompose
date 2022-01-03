/*
 * Copyright (C) 2021 HyejeanMOON.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyejeanmoon.splashcompose.screen.collections

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.rememberCoilPainter
import com.hyejeanmoon.splashcompose.ErrorAlert
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.screen.collectionphotos.PhotosOfCollectionActivity
import com.hyejeanmoon.splashcompose.screen.photos.PhotoUserInfo
import com.hyejeanmoon.splashcompose.utils.PhotoUtils

@Composable
fun CollectionsScreen(
    modifier: Modifier = Modifier,
    collectionsViewModel: CollectionsViewModel = hiltViewModel()
) {
    val viewStates = collectionsViewModel.viewStates
    val pagingItems = viewStates.pagingData.collectAsLazyPagingItems()
    var listState = if (pagingItems.itemCount > 0) viewStates.listState else LazyListState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = listState
    ) {
        items(pagingItems) { collectionsItem ->
            collectionsItem?.also {
                val item by remember { mutableStateOf(collectionsItem) }
                CollectionsItem(
                    collections = item,
                    resolution = collectionsViewModel.resolution
                )
            }
        }
    }

    pagingItems.apply {
        when {
            loadState.refresh is LoadState.Error -> {
                ErrorAlert()
            }
            loadState.append is LoadState.Error -> {
                ErrorAlert()
            }
            loadState.prepend is LoadState.Error -> {
                ErrorAlert()
            }
        }
    }
}

@Composable
fun CollectionsItem(
    modifier: Modifier = Modifier,
    isShowUserInfo: Boolean = true,
    resolution: String,
    collections: Collections
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        if (isShowUserInfo) {
            PhotoUserInfo(
                userName = collections.user?.userName.orEmpty(),
                userPhoto = collections.user?.profileImage?.large.orEmpty()
            )
        }

        ConstraintLayout(
            modifier = modifier
                .padding(20.dp, 10.dp, 20.dp, 0.dp)
                .fillMaxWidth()
                .requiredHeight(300.dp)
                .clickable {
                    PhotosOfCollectionActivity.start(
                        context,
                        collections.id.orEmpty(),
                        collections.title.orEmpty()
                    )
                }
        ) {

            val coverPhotoUrl = PhotoUtils.getCoverPhotoOfCollectionUrlByResolution(
                resolution,
                collections
            )

            val (image, title, photoNumber, userInfo) = createRefs()

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(300.dp)
                    .constrainAs(image) {
                        start.linkTo(image.start)
                        end.linkTo(image.end)
                        top.linkTo(userInfo.bottom)
                        bottom.linkTo(image.bottom)
                    }
                    .clip(RoundedCornerShape(10.dp)),
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
}