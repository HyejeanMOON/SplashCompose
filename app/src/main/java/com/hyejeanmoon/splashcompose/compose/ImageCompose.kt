package com.hyejeanmoon.splashcompose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun PhotoImage(
    modifier: Modifier = Modifier,
    photoUrl: String,
    photoName: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp,1.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = rememberGlidePainter(
                photoUrl,
                fadeIn = true,
                requestBuilder = {
                    diskCacheStrategy(DiskCacheStrategy.ALL)
                }
            ),
            contentDescription = "photo image",
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun PhotoListCoverImage(
    modifier: Modifier = Modifier,
    photoListCoverUrl: String,
    photoListName: String,
    photoListNumber: Int,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {

        val (image, text) = createRefs()
        Image(
            modifier = modifier.constrainAs(image) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            painter = rememberGlidePainter(
                photoListCoverUrl,
                fadeIn = true,
                requestBuilder = {
                    diskCacheStrategy(DiskCacheStrategy.NONE)
                }
            ),
            contentDescription = "photoListCover image"
        )

        Text(
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(parent.bottom, margin = 20.dp)
                end.linkTo(parent.end, margin = 20.dp)
            },
            text = photoListName,
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

    }
}