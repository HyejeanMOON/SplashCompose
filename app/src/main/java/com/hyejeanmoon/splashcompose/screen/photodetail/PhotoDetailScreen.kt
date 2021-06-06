package com.hyejeanmoon.splashcompose.screen.photodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.accompanist.glide.rememberGlidePainter
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.entity.Photo
import java.util.logging.SimpleFormatter


@Composable
fun PhotoDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailViewModel
) {

    val photo by viewModel.photo.observeAsState()

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(state = scrollState)
    ) {
        PhotoDetailImg(photo = photo)
        PhotoDetailUserInfo(photo = photo)
//        PhotoDetailCreatedTimes(photo = photo)

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

@Composable
fun PhotoDetailImg(
    modifier: Modifier = Modifier,
    photo: Photo?
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(500.dp),
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
}

@Composable
fun PhotoDetailUserInfo(
    modifier: Modifier = Modifier,
    photo: Photo?
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(50.dp)
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        val (userPhoto, userName, downloadIcon, favoriteIcon) = createRefs()

        Image(
            modifier = Modifier
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
        Text(
            modifier = Modifier
                .constrainAs(userName) {
                    start.linkTo(userPhoto.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = photo?.user?.name.orEmpty(),
            color = Color.Black,
            fontSize = 16.sp
        )
        Image(
            modifier = Modifier
                .padding(20.dp, 5.dp)
                .constrainAs(favoriteIcon) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            painter = painterResource(id = R.drawable.ic_favorites),
            contentDescription = "favorite icon"
        )

        Image(
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .constrainAs(downloadIcon) {
                    end.linkTo(favoriteIcon.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
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
            .requiredHeight(30.dp)
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
            .requiredHeight(40.dp)
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
            color = Color.Black,
            fontWeight = FontWeight.Bold
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

