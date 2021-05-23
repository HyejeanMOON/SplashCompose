package com.hyejeanmoon.splashcompose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PhotoScreen(
    modifier:Modifier = Modifier
){
    val coroutinesScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
    ) {
        
    }
}

@Preview
@Composable
fun PreviewPhotoScreen(){
    PhotoScreen()
}