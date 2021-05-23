package com.hyejeanmoon.splashcompose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun PhotoImage(
    modifier: Modifier = Modifier,
    photoUrl: String,
    photoName: String,
    isPreviewMode: Boolean = false
) {
    val isPreviewModeState by remember { mutableStateOf(isPreviewMode) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        if (isPreviewModeState) {
            Image(
                painter = painterResource(id = R.drawable.ic_none_picture),
                contentDescription = "photoListCover image"
            )
        } else {
            Image(
                painter = rememberGlidePainter(
                    photoUrl,
                    fadeIn = true,
                    requestBuilder = {
                        diskCacheStrategy(DiskCacheStrategy.NONE)
                    }
                ),
                contentDescription = "photo image"
            )
        }
    }

}

@Composable
fun PhotoListCoverImage(
    modifier: Modifier = Modifier,
    photoListCoverUrl: String,
    photoListName: String,
    photoListNumber: Int,
    isPreviewMode: Boolean = false
) {
    val isPreviewModeState by remember { mutableStateOf(isPreviewMode) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {

        val (image, text) = createRefs()

        if (isPreviewModeState) {
            Image(
                modifier = modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                painter = painterResource(id = R.drawable.ic_none_picture),
                contentDescription = "photoListCover image"
            )
        } else {
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
        }

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

@Preview(showBackground = true)
@Composable
fun PreviewPhotoImage() {
    PhotoImage(
        photoUrl = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d",
        photoName = "Photo Name",
        isPreviewMode = true
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoListCoverImage() {
    Surface{
        PhotoListCoverImage(
            photoListCoverUrl = "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d",
            photoListName = "Photo List Name",
            photoListNumber = 12,
            isPreviewMode = true
        )
    }
}