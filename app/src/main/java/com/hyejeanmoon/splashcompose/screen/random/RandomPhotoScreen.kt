package com.hyejeanmoon.splashcompose.screen.random

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
            targetValue = if (isRotated) Color.Red  else Color.White,
            animationSpec = tween(
                durationMillis = 800,
                easing = FastOutSlowInEasing
            ),
            finishedListener = {
                isRotated = false
            }
        )

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