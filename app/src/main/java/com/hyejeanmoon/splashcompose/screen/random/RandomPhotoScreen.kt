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

package com.hyejeanmoon.splashcompose.screen.random

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.ui.theme.TransparentMoonGray

@Composable
fun RandomPhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: RandomPhotoViewModel,
    onRandomPhotoClick: (String) -> Unit,
    onUserInfoClick: (String) -> Unit
) {

    val randomPhoto by viewModel.randomPhoto.observeAsState()

    ConstraintLayout(modifier = modifier) {

        val (image,userInfo) = createRefs()

        // photo
        Image(
            modifier = Modifier
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            painter = rememberGlidePainter(
                request = randomPhoto?.urls?.full.orEmpty(),
                fadeIn = true,
                requestBuilder = {
                    diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                }
            ),
            contentDescription = "Random Photo",
            contentScale = ContentScale.Crop
        )

        // user info
        RandomPhotoUserInfo(
            modifier = Modifier
                .requiredHeight(55.dp)
                .constrainAs(userInfo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            viewModel = viewModel,
            onRandomPhotoClick = onRandomPhotoClick,
            onUserInfoClick = onUserInfoClick,
            randomPhoto = randomPhoto
        )
    }
}

@Composable
fun RandomPhotoUserInfo(
    modifier: Modifier = Modifier,
    viewModel: RandomPhotoViewModel,
    randomPhoto: Photo?,
    onUserInfoClick: (String) -> Unit,
    onRandomPhotoClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(TransparentMoonGray)
    ) {

        val (userPhoto, userName, info, refresh) = createRefs()

        var isRotated by remember { mutableStateOf(false) }

        val angle: Float by animateFloatAsState(
            targetValue = if (isRotated) 360F else 0F,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            finishedListener = {
                isRotated = false
            }
        )

        val color: Color by animateColorAsState(
            targetValue = if (isRotated) Color.Red else Color.Black,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            finishedListener = {
                isRotated = false
            }
        )

        // user icon
        Image(
            modifier = Modifier
                .clickable {
                    onUserInfoClick(randomPhoto?.user?.userName.orEmpty())
                }
                .padding(20.dp, 10.dp)
                .clip(CircleShape)
                .constrainAs(userPhoto) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            painter = rememberGlidePainter(
                request = randomPhoto?.user?.profileImage?.large.orEmpty(),
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
                    onUserInfoClick(randomPhoto?.user?.userName.orEmpty())
                }
                .constrainAs(userName) {
                    start.linkTo(userPhoto.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = randomPhoto?.user?.userName.orEmpty(),
            color = Color.Black,
            fontSize = 16.sp
        )

        // info icon
        Icon(
            modifier = Modifier
                .constrainAs(info) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(0.dp, 0.dp, 20.dp, 0.dp)
                .clickable {
                    onRandomPhotoClick(randomPhoto?.id.orEmpty())
                },
            painter = painterResource(id = R.drawable.ic_info_outline),
            contentDescription = "Information Icon",
            tint = Color.Black
        )

        // refresh icon
        Icon(
            modifier = Modifier
                .constrainAs(refresh) {
                    top.linkTo(info.top)
                    bottom.linkTo(info.bottom)
                    end.linkTo(info.start)
                }
                .padding(0.dp, 0.dp, 15.dp, 0.dp)
                .clickable {
                    isRotated = true
                    viewModel.getRandomPhoto()
                }
                .rotate(angle),
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = "Refresh Icon",
            tint = color
        )
    }
}