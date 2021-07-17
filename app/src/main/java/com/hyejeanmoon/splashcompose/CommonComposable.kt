package com.hyejeanmoon.splashcompose

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hyejeanmoon.splashcompose.ui.theme.MoonGray

@Composable
fun SetUpStatusBar() {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setStatusBarColor(
            color = MoonGray,
            darkIcons = useDarkIcons
        )
    }
}

@Composable
fun ErrorAlert(
    onDismissCallback: () -> Unit
) {
    var showDialog by remember {
        mutableStateOf(true)
    }
    if (showDialog) {
        AlertDialog(
            title = { Text(text = "Error") },
            text = { Text(text = "There are some errors!\nPlease retry later.") },
            onDismissRequest = onDismissCallback,
            buttons = { showDialog = false }
        )
    }
}