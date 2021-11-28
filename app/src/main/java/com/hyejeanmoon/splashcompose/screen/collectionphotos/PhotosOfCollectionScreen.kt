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

package com.hyejeanmoon.splashcompose.screen.collectionphotos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hyejeanmoon.splashcompose.ErrorAlert
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.photos.PhotoImage

@Composable
fun PhotosOfCollectionScreen(
    modifier: Modifier = Modifier,
    photosOfCollectionViewModel: PhotosOfCollectionViewModel = hiltViewModel(),
    onPhotoClick: (Photo?) -> Unit,
    onBackIconClick: () -> Unit,
    onUserInfoClick: (String) -> Unit,
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
                        imageVector = Icons.Filled.ArrowBack,
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
            items(pagingItems) { photoItem ->
                val item by remember {
                    mutableStateOf(photoItem)
                }
                item?.also { photo ->
                    PhotoImage(
                        photo = photo,
                        onPhotoClick = onPhotoClick,
                        resolution = photosOfCollectionViewModel.resolution,
                        onUserInfoClick = { onUserInfoClick(photo.user?.userName.orEmpty()) }
                    )
                }
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