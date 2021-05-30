package com.hyejeanmoon.splashcompose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter

@Composable
fun PhotoImage(
    modifier: Modifier = Modifier,
    photoUrl: String
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 1.dp),
        painter = rememberGlidePainter(
            photoUrl,
            fadeIn = true,
            requestBuilder = {
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            }
        ),
        contentDescription = "photo image",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun CollectionsImage(
    modifier: Modifier = Modifier,
    coverUrl: String
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 1.dp),
        painter = rememberGlidePainter(
            coverUrl,
            fadeIn = true,
            requestBuilder = {
                diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            }
        ),
        contentDescription = "collections cover image",
        contentScale = ContentScale.FillWidth
    )
}