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
import com.hyejeanmoon.splashcompose.ErrorAlert
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.utils.PhotoUtils

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
        items(pagingItems) { photoItem ->
            val item by remember {
                mutableStateOf(photoItem)
            }
            PhotoImage(
                photo = item,
                resolution = viewModel.resolution,
                onPhotoClick = onPhotoClick
            )
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
fun PhotoImage(
    modifier: Modifier = Modifier,
    photo: Photo?,
    resolution: String,
    onPhotoClick: (Photo?) -> Unit
) {

    val photoUrl = PhotoUtils.getPhotoUrlByResolution(
        resolution,
        photo
    )

    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 1.dp)
            .clickable { onPhotoClick(photo) },
        painter = rememberCoilPainter(
            photoUrl,
            fadeIn = true
        ),
        contentDescription = "photo image",
        contentScale = ContentScale.FillWidth
    )
}

