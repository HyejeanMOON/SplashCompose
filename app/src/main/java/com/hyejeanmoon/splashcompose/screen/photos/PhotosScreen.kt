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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.ErrorAlert
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.ui.theme.TransparentMoonGray
import com.hyejeanmoon.splashcompose.utils.PhotoUtils

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel,
    onPhotoClick: (Photo?) -> Unit,
    onUserInfoClick: (String) -> Unit
) {
    val pagingItems = viewModel.photoList.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(pagingItems) { photoItem ->
            val item by remember {
                mutableStateOf(photoItem)
            }
            item?.also {
                PhotoImage(
                    photo = it,
                    resolution = viewModel.resolution,
                    onPhotoClick = onPhotoClick,
                    onUserInfoClick = onUserInfoClick
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
fun PhotoImage(
    modifier: Modifier = Modifier,
    photo: Photo,
    resolution: String,
    onPhotoClick: (Photo) -> Unit,
    onUserInfoClick: (String) -> Unit
) {
    val photoUrl = PhotoUtils.getPhotoUrlByResolution(
        resolution,
        photo
    )

    ConstraintLayout(modifier = modifier) {
        val (photoRef, userInfo) = createRefs()

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(450.dp)
                .constrainAs(photoRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(0.dp, 1.dp)
                .clickable { onPhotoClick(photo) },
            painter = rememberCoilPainter(
                photoUrl,
                fadeIn = true
            ),
            contentDescription = "photo image",
            contentScale = ContentScale.Crop
        )

        PhotoUserInfo(
            modifier = Modifier.constrainAs(userInfo) {
                top.linkTo(photoRef.top)
                start.linkTo(photoRef.start)
            },
            photo = photo,
            onUserInfoClick = onUserInfoClick
        )
    }
}

@Composable
fun PhotoUserInfo(
    modifier: Modifier = Modifier,
    photo: Photo,
    onUserInfoClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .requiredHeight(50.dp)
            .background(TransparentMoonGray)
    ) {
        val (userPhoto, userName) = createRefs()

        // user icon
        Image(
            modifier = Modifier
                .clickable {
                    onUserInfoClick(photo.user?.userName.orEmpty())
                }
                .padding(20.dp, 10.dp)
                .clip(CircleShape)
                .constrainAs(userPhoto) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            painter = rememberGlidePainter(
                request = photo.user?.profileImage?.large.orEmpty(),
                fadeIn = true,
                requestBuilder = {
                    diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                }
            ),
            contentDescription = "user profile image"
        )

        // user name
        Text(
            modifier = Modifier
                .clickable {
                    onUserInfoClick(photo.user?.userName.orEmpty())
                }
                .constrainAs(userName) {
                    start.linkTo(userPhoto.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = photo.user?.userName.orEmpty(),
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}
