package com.hyejeanmoon.splashcompose.screen.userdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.UserDetail
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailLocation

@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel,
    onBackIconClick: () -> Unit
) {
    val userDetail by viewModel.userDetail.observeAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            onBackIconClick()
                        },
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "back icon",
                        tint = Color.Black
                    )
                },
                title = {
                    Text(
                        text = userDetail?.userName.orEmpty(),
                        color = Color.Black
                    )
                }
            )
        }
    ) {
        val verticalScrollState = rememberScrollState()
        ConstraintLayout(
            modifier = modifier.verticalScroll(verticalScrollState)
        ) {
            val (coverPhoto, userName, statics, location, bio, pager) = createRefs()

            Image(
                modifier = Modifier
                    .padding(30.dp, 20.dp, 30.dp, 10.dp)
                    .size(80.dp)
                    .clip(CircleShape)
                    .constrainAs(coverPhoto) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                painter = rememberGlidePainter(
                    request = userDetail?.profileImage?.large.orEmpty(),
                    fadeIn = true,
                    requestBuilder = {
                        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    }
                ),
                contentDescription = "user cover photo"
            )

            Text(
                modifier = Modifier
                    .padding(0.dp, 30.dp, 40.dp, 0.dp)
                    .constrainAs(userName) {
                        start.linkTo(coverPhoto.end)
                        top.linkTo(coverPhoto.top)
                        bottom.linkTo(statics.top)
                    }
                    .fillMaxWidth(),
                text = userDetail?.name.orEmpty(),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            UserDetailStatics(
                modifier = Modifier
                    .padding(0.dp, 10.dp, 40.dp, 20.dp)
                    .constrainAs(statics) {
                        bottom.linkTo(coverPhoto.bottom)
                        start.linkTo(coverPhoto.end)
                        top.linkTo(userName.bottom)
                    },
                userDetail = userDetail
            )


            // Location
            if (userDetail?.location != null) {
                PhotoDetailLocation(
                    modifier = Modifier.constrainAs(location) {
                        start.linkTo(parent.start)
                        top.linkTo(coverPhoto.bottom)
                    },
                    location = userDetail?.location.orEmpty()
                )
            }

            // Biography
            if (userDetail?.bio != null) {
                Text(
                    modifier = Modifier
                        .constrainAs(bio) {
                            start.linkTo(coverPhoto.start)
                            if (userDetail?.location != null) {
                                top.linkTo(location.bottom)
                            } else {
                                top.linkTo(coverPhoto.bottom)
                            }
                        }
                        .padding(20.dp, 10.dp, 20.dp, 0.dp),
                    text = userDetail?.bio.orEmpty(),
                    color = Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun UserDetailStatics(
    modifier: Modifier = Modifier,
    userDetail: UserDetail?
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (photos, likes, collections) = createRefs()

        // Photos
        UserDetailStaticsItem(
            modifier = Modifier.constrainAs(photos) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            number = userDetail?.totalPhotos,
            itemName = "Photos"
        )

        // Likes
        UserDetailStaticsItem(
            modifier = Modifier
                .constrainAs(likes) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(photos.end)
                    end.linkTo(collections.start)
                }
                .padding(30.dp, 0.dp, 30.dp, 0.dp),
            number = userDetail?.totalLikes,
            itemName = "Likes"
        )

        // Collections
        UserDetailStaticsItem(
            modifier = Modifier.constrainAs(collections) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            number = userDetail?.totalCollections,
            itemName = "Collections"
        )

    }
}

@Composable
fun UserDetailStaticsItem(
    modifier: Modifier = Modifier,
    number: Int?,
    itemName: String
) {
    if (number != null) {
        ConstraintLayout(modifier = modifier) {
            val (num, name) = createRefs()
            Text(
                modifier = Modifier.constrainAs(num) {
                    start.linkTo(name.start)
                    end.linkTo(name.end)
                    bottom.linkTo(name.top)
                },
                text = number.toString(),
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(num.bottom)
                },
                text = itemName,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

//@Composable
//fun UserDetailPhotos(
//    modifier: Modifier = Modifier,
//    photos: List<Photo>
//) {
//    LazyColumn(
//        modifier = modifier.fillMaxSize()
//    ) {
//        items(items = photos) { photo ->
//            Image(
//                modifier = Modifier.fillMaxWidth(),
//                painter = rememberGlidePainter(
//                    request = photo.urls?.regular.orEmpty(),
//                    fadeIn = true,
//                    requestBuilder = {
//                        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    }
//                ),
//                contentDescription = "user's photos"
//            )
//        }
//    }
//}

@Preview
@Composable
fun PreviewUserDetailStaticsItem() {
    UserDetailStaticsItem(number = 12, itemName = "Photos")
}