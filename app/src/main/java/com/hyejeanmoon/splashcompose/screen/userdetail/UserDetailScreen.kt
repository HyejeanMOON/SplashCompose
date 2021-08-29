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

package com.hyejeanmoon.splashcompose.screen.userdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.hyejeanmoon.splashcompose.ErrorAlert
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.UserDetail
import com.hyejeanmoon.splashcompose.entity.UsersPhotos
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsItem
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailLocation
import com.hyejeanmoon.splashcompose.screen.photos.PhotoUserInfo
import com.hyejeanmoon.splashcompose.utils.PhotoUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel,
    onBackIconClick: () -> Unit,
    onCollectionItemsClick: (String, String) -> Unit,
    onPhotoClick: (UsersPhotos?) -> Unit,
    onUserInfoClick: (String) -> Unit
) {
    val userDetail by viewModel.userDetail.observeAsState()
    val exception by viewModel.exception.observeAsState()

    val coroutines = rememberCoroutineScope()
    var visible by remember { mutableStateOf(true) }
    var clickCount by remember { mutableStateOf(0) }

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
        ConstraintLayout {
            val (visibleLayout, tabrow, pager) = createRefs()

            AnimatedVisibility(
                modifier = Modifier.constrainAs(visibleLayout) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(tabrow.top)
                },
                visible = visible
            ) {
                ConstraintLayout {
                    val (coverPhoto, userName, statics, location, bio) = createRefs()

                    // profile image
                    Image(
                        modifier = Modifier
                            .padding(30.dp, 20.dp, 30.dp, 10.dp)
                            .size(80.dp)
                            .clip(CircleShape)
                            .constrainAs(coverPhoto) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                            },
                        painter = rememberCoilPainter(
                            request = userDetail?.profileImage?.large.orEmpty(),
                            fadeIn = true
                        ),
                        contentDescription = "user cover photo"
                    )

                    // name text
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
                            modifier = Modifier
                                .constrainAs(location) {
                                    start.linkTo(parent.start)
                                    top.linkTo(coverPhoto.bottom)
                                }
                                .padding(0.dp, 0.dp, 0.dp, 10.dp),
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
                                .padding(20.dp, 0.dp, 20.dp, 10.dp),
                            text = userDetail?.bio.orEmpty(),
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            userDetail?.also {
                // TabRow
                val pagesMutableList: MutableList<String> = mutableListOf()

                if (userDetail?.totalPhotos ?: 0 > 0) {
                    pagesMutableList.add(stringResource(id = R.string.tabrow_photos))
                }

                if (userDetail?.totalCollections ?: 0 > 0) {
                    pagesMutableList.add(stringResource(id = R.string.tabrow_collections))
                }

                if (userDetail?.totalLikes ?: 0 > 0) {
                    pagesMutableList.add(stringResource(id = R.string.tabrow_liked_photos))
                }

                val pages: List<String> = pagesMutableList.toList()

                val pagerState = rememberPagerState(
                    pageCount = pages.size
                )

                if (pagerState.pageCount > 0) {
                    TabRow(
                        modifier = Modifier
                            .constrainAs(tabrow) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                if (visible) {
                                    top.linkTo(visibleLayout.bottom)
                                } else {
                                    top.linkTo(parent.top)
                                }
                            },
                        // Our selected tab is our current page
                        selectedTabIndex = pagerState.currentPage,
                        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier.pagerTabIndicatorOffset(
                                    pagerState,
                                    tabPositions
                                ),
                                color = Color.Red
                            )
                        }
                    ) {
                        // Add tabs for all of our pages
                        pages.forEachIndexed { index, title ->
                            Tab(
                                text = {
                                    Text(
                                        text = title,
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                },
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutines.launch(Dispatchers.IO) {
                                        pagerState.scrollToPage(
                                            page = index
                                        )
                                    }
                                    if (pagerState.currentPage == index) {
                                        clickCount += 1
                                        visible = clickCount % 2 == 0
                                    }
                                },
                            )
                        }
                    }

                    // Pagers
                    HorizontalPager(
                        modifier = Modifier
                            .constrainAs(pager) {
                                top.linkTo(tabrow.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .fillMaxHeight(),
                        state = pagerState
                    ) {
                        PageContent(
                            currentPage = pagerState.currentPage,
                            pages = pages,
                            viewModel = viewModel,
                            onPhotoClick = onPhotoClick,
                            onCollectionItemsClick = onCollectionItemsClick,
                            onUserInfoClick = onUserInfoClick
                        )
                    }
                }
            }
        }

        exception?.also {
            ErrorAlert()
        }
    }
}

@Composable
fun PageContent(
    currentPage: Int,
    pages: List<String>,
    viewModel: UserDetailViewModel,
    onPhotoClick: (UsersPhotos?) -> Unit,
    onCollectionItemsClick: (String, String) -> Unit,
    onUserInfoClick: (String) -> Unit
) {
    when (pages[currentPage]) {
        stringResource(id = R.string.tabrow_photos) -> {
            UserDetailPhotos(
                viewModel = viewModel,
                onPhotoClick = onPhotoClick
            )
        }
        stringResource(id = R.string.tabrow_collections) -> {
            UserDetailCollections(
                viewModel = viewModel,
                onCollectionItemsClick = onCollectionItemsClick
            )
        }
        stringResource(id = R.string.tabrow_liked_photos) -> {
            UserDetailLikedPhotos(
                viewModel = viewModel,
                onPhotoClick = onPhotoClick,
                onUserInfoClick = onUserInfoClick
            )
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

@Composable
fun UserDetailPhotos(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel,
    onPhotoClick: (UsersPhotos?) -> Unit
) {
    val photos = viewModel.userDetailPhotosFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(photos) { photoItem ->
            val item by remember {
                mutableStateOf(photoItem)
            }

            PhotoDetailImage(
                modifier = Modifier.fillMaxWidth(),
                photo = item,
                onPhotoClick = { onPhotoClick(item) },
                resolution = viewModel.resolution,
                onUserInfoClick = {
                    // do nothing
                },
                isShowUserInfo = false
            )
        }
    }
    photos.apply {
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
fun UserDetailCollections(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel,
    onCollectionItemsClick: (String, String) -> Unit
) {
    val collections = viewModel.userDetailCollectionsFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(collections) { collectionsItem ->
            val item by remember {
                mutableStateOf(collectionsItem)
            }

            item?.also {
                CollectionsItem(
                    collections = it,
                    resolution = viewModel.resolution,
                    onCollectionsItemClick = { id, title ->
                        onCollectionItemsClick(id, title)
                    },
                    onUserInfoClick = {
                        // do nothing
                    },
                    isShowUserInfo = false
                )
            }
        }
    }
    collections.apply {
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
fun UserDetailLikedPhotos(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel,
    onPhotoClick: (UsersPhotos?) -> Unit,
    onUserInfoClick: (String) -> Unit
) {
    val photos =
        viewModel.userDetailLikedPhotosDataSourceFlow.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(photos) { photoItem ->
            val item by remember {
                mutableStateOf(photoItem)
            }

            PhotoDetailImage(
                modifier = Modifier.fillMaxWidth(),
                photo = item,
                onPhotoClick = { onPhotoClick(item) },
                resolution = viewModel.resolution,
                onUserInfoClick = onUserInfoClick
            )
        }
    }
    photos.apply {
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
fun PhotoDetailImage(
    modifier: Modifier = Modifier,
    isShowUserInfo: Boolean = true,
    photo: UsersPhotos?,
    resolution: String,
    onPhotoClick: (UsersPhotos?) -> Unit,
    onUserInfoClick: (String) -> Unit,
) {

    val photoUrl = PhotoUtils.getUserDetailPhotoUrlByResolution(
        resolution,
        photo
    )

    Column(
        modifier = modifier
    ) {
        if (isShowUserInfo) {
            PhotoUserInfo(
                userName = photo?.user?.userName.orEmpty(),
                userPhoto = photo?.user?.profileImage?.large.orEmpty(),
                onUserInfoClick = onUserInfoClick,
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 0.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onPhotoClick(photo) },
            painter = rememberCoilPainter(
                photoUrl,
                fadeIn = true
            ),
            contentDescription = "photo image",
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
fun PreviewUserDetailStaticsItem() {
    UserDetailStaticsItem(number = 12, itemName = "Photos")
}