package com.hyejeanmoon.splashcompose.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CollectionsTitleText(
    modifier: Modifier = Modifier,
    collectionsName: String
) {
    Text(
        text = collectionsName,
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        color = Color.White
    )
}

@Composable
fun CollectionsNumbersText(
    modifier: Modifier = Modifier,
    numberOfCollections: String
) {
    Text(
        text = "$numberOfCollections photos",
        modifier = modifier,
        textAlign = TextAlign.End,
        fontSize = 18.sp,
        color = Color.White
    )
}

@Preview
@Composable
fun PreviewCollectionsNameText() {
    CollectionsTitleText(collectionsName = "SplashCompose")
}

@Preview
@Composable
fun PreviewCollectionsNumbersText() {
    CollectionsNumbersText(numberOfCollections = "10")
}

