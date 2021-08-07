package com.hyejeanmoon.splashcompose.screen.random

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.R

@Composable
fun RandomPhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: RandomPhotoViewModel,
    onRandomPhotoClick: (String) -> Unit
) {

    val randomPhoto by viewModel.randomPhoto.observeAsState()

    ConstraintLayout(modifier = modifier) {

        val (image, info, refresh) = createRefs()

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
        Icon(
            modifier = Modifier
                .constrainAs(info) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .padding(20.dp)
                .clickable {
                    onRandomPhotoClick(randomPhoto?.id.orEmpty())
                },
            painter = painterResource(id = R.drawable.ic_info_outline),
            contentDescription = "Information Icon",
            tint = Color.White
        )
        Icon(
            modifier = Modifier
                .constrainAs(refresh) {
                    top.linkTo(info.top)
                    bottom.linkTo(info.bottom)
                    end.linkTo(info.start)
                }
                .clickable {
                    viewModel.getRandomPhoto()
                },
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = "Refresh Icon",
            tint = Color.White
        )
    }
}