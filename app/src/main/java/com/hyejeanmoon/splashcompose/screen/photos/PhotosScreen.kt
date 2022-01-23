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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.hyejeanmoon.splashcompose.ErrorAlert
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailActivity
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailActivity
import com.hyejeanmoon.splashcompose.utils.PhotoUtils

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotosViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingPhotoList.pagingPhotoList.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(pagingItems) { photoItem ->
            val item by remember {
                mutableStateOf(photoItem)
            }
            item?.also {
                PhotoImage(
                    photo = it
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
    photo: Photo
) {
    val context = LocalContext.current
    val resolution = PhotoUtils.getPhotoResolutionFromPref(context)

    val photoUrl = PhotoUtils.getPhotoUrlByResolution(
        resolution,
        photo
    )

    ConstraintLayout(modifier = modifier) {
        val (photoRef, userInfo) = createRefs()

        PhotoUserInfo(
            modifier = Modifier
                .constrainAs(userInfo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(0.dp, 10.dp, 0.dp, 10.dp),
            userName = photo.user?.userName.orEmpty(),
            userPhoto = photo.user?.profileImage?.large.orEmpty()
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
                .constrainAs(photoRef) {
                    top.linkTo(userInfo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(20.dp, 0.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { PhotoDetailActivity.start(photo.id ?: "", context) },
            painter = rememberImagePainter(
                photoUrl
            ),
            contentDescription = "photo image",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PhotoUserInfo(
    modifier: Modifier = Modifier,
    userName: String,
    userPhoto: String,
    textColor: Color = Color.Black
) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .requiredHeight(50.dp)
    ) {
        val (userPhotoRef, userNameRef) = createRefs()

        // user icon
        Image(
            modifier = Modifier

                .padding(20.dp, 20.dp, 20.dp, 0.dp)
                .clip(CircleShape)
                .constrainAs(userPhotoRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {
                    UserDetailActivity.startUserDetailActivity(
                        context,
                        userName
                    )
                },
            painter = rememberImagePainter(
                userPhoto
            ),
            contentDescription = "user profile image"
        )

        // user name
        Text(
            modifier = Modifier
                .constrainAs(userNameRef) {
                    start.linkTo(userPhotoRef.end)
                    top.linkTo(userPhotoRef.top)
                    bottom.linkTo(userPhotoRef.bottom)
                }
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                .clickable {
                    UserDetailActivity.startUserDetailActivity(
                        context,
                        userName
                    )
                },
            text = userName,
            color = textColor,
            fontSize = 16.sp
        )
    }
}
