package com.hyejeanmoon.splashcompose.screen.userdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.UserDetail
import com.hyejeanmoon.splashcompose.entity.UsersPhotos
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsItem
import com.hyejeanmoon.splashcompose.screen.photodetail.PhotoDetailLocation
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
    onPhotoClick: (UsersPhotos?) -> Unit
) {
    val userDetail by viewModel.userDetail.observeAsState()

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
                                .padding(20.dp, 10.dp, 20.dp, 10.dp),
                            text = userDetail?.bio.orEmpty(),
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // TabRow
            val pages = listOf(
                stringResource(id = R.string.tabrow_photos),
                stringResource(id = R.string.tabrow_collections),
                stringResource(id = R.string.tabrow_liked_photos)
            )

            val pagerState = rememberPagerState(
                pageCount = pages.size
            )
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
                        modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
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
                when (pagerState.currentPage) {
                    0 -> {
                        UserDetailPhotos(
                            viewModel = viewModel,
                            onPhotoClick = onPhotoClick
                        )
                    }
                    1 -> {
                        UserDetailCollections(
                            viewModel = viewModel,
                            onCollectionItemsClick = onCollectionItemsClick
                        )
                    }
                    2 -> {
                        UserDetailLikedPhotos(
                            viewModel = viewModel,
                            onPhotoClick = onPhotoClick
                        )
                    }
                }
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

@Composable
fun UserDetailPhotos(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel,
    onPhotoClick: (UsersPhotos?) -> Unit
) {
    val photos = viewModel.userDetailPhotosFlow.collectAsLazyPagingItems()

    if (photos.itemCount > 0) {
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
                    resolution = viewModel.resolution
                )
            }
        }
        photos.apply {
            when {
                loadState.refresh is LoadState.Error -> {

                }
                loadState.append is LoadState.Error -> {

                }
            }
        }
    } else {
        NoPhotos(title = "photos")
    }
}

@Composable
fun UserDetailCollections(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel,
    onCollectionItemsClick: (String, String) -> Unit
) {
    val collections = viewModel.userDetailCollectionsFlow.collectAsLazyPagingItems()

    if (collections.itemCount > 0) {
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
                        resolution = viewModel.resolution
                    ) { id, title ->
                        onCollectionItemsClick(id, title)
                    }
                }
            }
        }
        collections.apply {
            when {
                loadState.refresh is LoadState.Error -> {

                }
                loadState.append is LoadState.Error -> {

                }
            }
        }
    } else {
        NoPhotos(title = "collections")
    }
}

@Composable
fun UserDetailLikedPhotos(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel,
    onPhotoClick: (UsersPhotos?) -> Unit
) {
    val photos =
        viewModel.userDetailLikedPhotosDataSourceFlow.collectAsLazyPagingItems()

    if (photos.itemCount > 0) {
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
                    resolution = viewModel.resolution
                )
            }
        }
        photos.apply {
            when {
                loadState.refresh is LoadState.Error -> {

                }
                loadState.append is LoadState.Error -> {

                }
            }
        }
    } else {
        NoPhotos(title = "liked photos")
    }
}

@Composable
fun NoPhotos(
    modifier: Modifier = Modifier,
    title: String
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp, 50.dp, 0.dp, 0.dp)
    ) {

        val (img, txt) = createRefs()

        Image(
            modifier = Modifier
                .size(128.dp)
                .constrainAs(img) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            painter = painterResource(id = R.mipmap.ic_no_favorite_photos),
            contentDescription = "no photos"
        )

        Text(
            modifier = Modifier
                .padding(0.dp, 40.dp, 0.dp, 0.dp)
                .constrainAs(txt) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(img.bottom)
                },
            text = "There are not $title!",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}

@Composable
fun PhotoDetailImage(
    modifier: Modifier = Modifier,
    photo: UsersPhotos?,
    resolution: String,
    onPhotoClick: (UsersPhotos?) -> Unit
) {

    val photoUrl = PhotoUtils.getUserDetailPhotoUrlByResolution(
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

@Preview
@Composable
fun PreviewUserDetailStaticsItem() {
    UserDetailStaticsItem(number = 12, itemName = "Photos")
}