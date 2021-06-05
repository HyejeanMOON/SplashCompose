package com.hyejeanmoon.splashcompose.compose

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.Collections


@Composable
fun CollectionsDetailText(
    modifier: Modifier = Modifier,
    collections: Collections
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(50.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(40.dp, 0.dp)
                .clip(CircleShape),
            painter = rememberGlidePainter(
                request = collections.user?.profileImage?.large,
                fadeIn = true,
                requestBuilder = {
                    diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                }
            ),
            contentDescription = "author image"
        )

        Text(
            modifier = Modifier.padding(40.dp, 0.dp),
            text = collections.title.orEmpty(),
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.End
        )

        Text(
            modifier = Modifier.padding(40.dp, 0.dp),
            text = "${collections.totalPhotos} photos",
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun SettingsItemTitle(
    modifier: Modifier = Modifier,
    titleName: String
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .size(60.dp)
    ) {
        val (text, divider) = createRefs()
        Text(
            modifier = Modifier
                .padding(20.dp, 0.dp, 0.dp, 0.dp)
                .constrainAs(text)
                {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = titleName,
            textAlign = TextAlign.Left,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Divider(
            modifier = Modifier.constrainAs(divider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )

    }
}

@Composable
fun SettingsItemDetail(
    modifier: Modifier = Modifier,
    itemName: String,
    onItemClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .size(50.dp)
            .clickable { onItemClick(itemName) }
    ) {
        val (text, icon, divider) = createRefs()
        Text(
            modifier = Modifier
                .padding(20.dp, 0.dp, 0.dp, 0.dp)
                .constrainAs(text) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = itemName,
            textAlign = TextAlign.Left,
            color = Color.Black,
            fontSize = 18.sp
        )

        Image(
            modifier = Modifier
                .size(24.dp)
                .constrainAs(icon) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(0.dp,0.dp,10.dp,0.dp),
            painter = painterResource(id = R.drawable.ic_right),
            contentDescription = "right icon"
        )

        Divider(
            modifier = Modifier.constrainAs(divider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsItemTitle() {
    SettingsItemTitle(titleName = "Others")
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsItem() {
    SettingsItemDetail(itemName = "Version") {

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettings() {
    Column {
        SettingsItemTitle(titleName = "Others")
        SettingsItemDetail(itemName = "Version") {

        }
        SettingsItemDetail(itemName = "About me") {

        }
    }
}

