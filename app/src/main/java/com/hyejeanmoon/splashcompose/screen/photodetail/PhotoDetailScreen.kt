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

package com.hyejeanmoon.splashcompose.screen.photodetail

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hyejeanmoon.splashcompose.ErrorAlert
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailActivity

@Composable
fun PhotoDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
    onBackIconClick: () -> Unit,
    onDownloadImage: () -> Unit
) {
    val photo by viewModel.photo.observeAsState()
    val scrollState = rememberScrollState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val isFavoritePhoto by viewModel.isFavoritePhoto.observeAsState()
    val exception by viewModel.exception.observeAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.getPhotoById() }
    ) {
        Column(
            modifier = modifier.verticalScroll(state = scrollState)
        ) {
            PhotoDetailImg(photo = photo, onBackIconClick = onBackIconClick)
            PhotoDetailUserInfo(
                photo = photo,
                viewModel = viewModel,
                isFavoritePhoto = isFavoritePhoto,
                onDownloadImage = onDownloadImage
            )

            photo?.also {
                val country = it.location?.country.orEmpty()
                val city = it.location?.city.orEmpty()
                if (country.isNotBlank() && city.isNotBlank()) {
                    PhotoDetailLocation(location = "$city, $country")
                } else if (city.isNotBlank()) {
                    PhotoDetailLocation(location = city)
                } else if (country.isNotBlank()) {
                    PhotoDetailLocation(location = country)
                }
            }

            PhotoDetailCreatedTimes(photo = photo)

            Divider(
                modifier = Modifier.padding(20.dp, 10.dp)
            )

            PhotoDetailExifInfo(photo = photo)

            Divider(
                modifier = Modifier.padding(20.dp, 10.dp)
            )

            PhotoDetailStaticsInfo(photo = photo)

            Divider(
                modifier = Modifier.padding(20.dp, 10.dp)
            )
        }
    }
    exception?.also {
        ErrorAlert()
    }
}

@Composable
fun PhotoDetailImg(
    modifier: Modifier = Modifier,
    photo: Photo?,
    onBackIconClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(500.dp)
    ) {
        val (image, icon) = createRefs()

        Image(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = rememberGlidePainter(
                request = photo?.urls?.full.orEmpty(),
                fadeIn = true,
                requestBuilder = {
                    diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                }
            ),
            contentDescription = "photo detail",
            contentScale = ContentScale.Crop
        )

        Icon(
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(image.top)
                    start.linkTo(image.start)
                }
                .padding(20.dp)
                .clickable { onBackIconClick() },
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "back icon",
            tint = Color.White
        )
    }
}

@Composable
fun PhotoDetailUserInfo(
    modifier: Modifier = Modifier,
    photo: Photo?,
    viewModel: PhotoDetailViewModel,
    isFavoritePhoto: Boolean?,
    onDownloadImage: () -> Unit
) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(50.dp)
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        val (userPhoto, userName, downloadIcon, favoriteIcon) = createRefs()

        // user icon
        Image(
            modifier = Modifier
                .clickable {
                    UserDetailActivity.startUserDetailActivity(
                        context,
                        photo?.user?.userName.orEmpty()
                    )
                }
                .padding(20.dp, 5.dp)
                .clip(CircleShape)
                .constrainAs(userPhoto) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            painter = rememberGlidePainter(
                request = photo?.user?.profileImage?.large.orEmpty(),
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
                    UserDetailActivity.startUserDetailActivity(
                        context,
                        photo?.user?.userName.orEmpty()
                    )
                }
                .constrainAs(userName) {
                    start.linkTo(userPhoto.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = photo?.user?.name.orEmpty(),
            color = Color.Black,
            fontSize = 16.sp
        )

        // favorite icon
        Image(
            modifier = Modifier
                .size(52.dp, 32.dp)
                .padding(0.dp, 5.dp, 20.dp, 5.dp)
                .constrainAs(favoriteIcon) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {
                    viewModel.favoritePhoto()
                },
            painter = if (isFavoritePhoto == true) {
                painterResource(id = R.drawable.ic_favorites_filled)
            } else {
                painterResource(id = R.drawable.ic_favorites)
            },
            contentDescription = "favorite icon"
        )

        // download icon
        Image(
            modifier = Modifier
                .size(42.dp, 32.dp)
                .padding(0.dp, 5.dp, 10.dp, 5.dp)
                .constrainAs(downloadIcon) {
                    end.linkTo(favoriteIcon.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        viewModel.downloadPhotoByIdVersionQ()
                    } else {
                        onDownloadImage()
                    }
                    viewModel.downloadPhotoById()
                },
            painter = painterResource(id = R.drawable.ic_download),
            contentDescription = "download icon"
        )
    }
}

@Composable
fun PhotoDetailCreatedTimes(
    modifier: Modifier = Modifier,
    photo: Photo?
) {
    Row(
        modifier = modifier
            .requiredHeight(25.dp)
            .padding(20.dp, 10.dp, 0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Created At: ${photo?.createdAt.orEmpty()}",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun PhotoDetailLocation(
    modifier: Modifier = Modifier,
    location: String
) {
    Row(
        modifier = modifier
            .requiredHeight(28.dp)
            .padding(20.dp, 10.dp, 0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // icon
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = "location icon"
        )

        // location text
        Text(
            modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp),
            text = location,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun PhotoDetailExifInfo(
    modifier: Modifier = Modifier,
    photo: Photo?
) {
    Column(
        modifier = modifier
    ) {

        // camera
        PhotoDetailInfoItem(
            iconResource = R.drawable.ic_camera,
            contentDescription = "camera icon",
            name = "Camera",
            content = photo?.exif?.model ?: "Null"

        )

        //　aperture
        PhotoDetailInfoItem(
            iconResource = R.drawable.ic_aperture,
            contentDescription = "aperture icon",
            name = "Aperture",
            content = photo?.exif?.aperture ?: "Null"
        )

        // focal length
        PhotoDetailInfoItem(
            iconResource = R.drawable.ic_focal_length,
            contentDescription = "focal length icon",
            name = "Focal Length",
            content = photo?.exif?.focalLength ?: "Null"
        )

        // exposure time
        PhotoDetailInfoItem(
            iconResource = R.drawable.ic_times,
            contentDescription = "exposure time icon",
            name = "Exposure Time",
            content = photo?.exif?.exposureTime ?: "Null"
        )

        // iso
        PhotoDetailInfoItem(
            iconResource = R.drawable.ic_iso,
            contentDescription = "iso icon",
            name = "ISO",
            content = photo?.exif?.iso?.toString() ?: "Null"
        )

        // size
        var size = "Null"
        if (photo?.height != null && photo.width != null) {
            size = "${photo.width} x ${photo.height}"
        }
        PhotoDetailInfoItem(
            iconResource = R.drawable.ic_size,
            contentDescription = "size icon",
            name = "Size",
            content = size
        )
    }
}

@Composable
fun PhotoDetailStaticsInfo(
    modifier: Modifier = Modifier,
    photo: Photo?
) {
    Column(modifier = modifier) {
        // download number
        PhotoDetailInfoItem(
            iconResource = R.drawable.ic_download,
            contentDescription = "download icon",
            name = "Downloads",
            content = photo?.downloads?.toString() ?: "-"
        )

        // likes number
        PhotoDetailInfoItem(
            iconResource = R.drawable.ic_favorites,
            contentDescription = "viewed icon",
            name = "Likes",
            content = photo?.likes?.toString() ?: "-"
        )
    }
}

@Composable
fun PhotoDetailInfoItem(
    modifier: Modifier = Modifier,
    iconResource: Int,
    contentDescription: String,
    name: String,
    content: String
) {
    Row(
        modifier = modifier
            .requiredHeight(40.dp)
            .padding(
                20.dp, 10.dp, 0.dp, 0.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // icon
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconResource),
            contentDescription = contentDescription
        )

        // name
        Text(
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 0.dp)
                .requiredWidth(130.dp),
            text = "${name}:",
            color = Color.Black,
            fontSize = 14.sp
        )

        // content
        Text(
            modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp),
            text = content,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

