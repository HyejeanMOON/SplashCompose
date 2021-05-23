package com.hyejeanmoon.splashcompose.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun PhotoListNameLabel(
    modifier:Modifier = Modifier,
    photoListName:String
){
    Text(
        text = photoListName,
        modifier = modifier.clickable {  },
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        color = Color.White
    )
}

@Composable
fun PhotoListNumberLabel(
    modifier: Modifier = Modifier,
    photoListNumber:Int
){
    Text(
        text = "$photoListNumber photos",
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        color = Color.White
    )
}

@Preview
@Composable
fun PreviewPhotoListLabel(){
    PhotoListNameLabel(photoListName = "PhotoList")
}

@Preview
@Composable
fun PreviewPhotoListNumberLabel(){
    PhotoListNumberLabel(photoListNumber = 12)
}

