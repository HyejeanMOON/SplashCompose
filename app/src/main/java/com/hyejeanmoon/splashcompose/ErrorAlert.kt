package com.hyejeanmoon.splashcompose

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun ErrorAlert(
    modifier: Modifier = Modifier,
    showAlert: Boolean = true
) {
    var openAlert by remember {
        mutableStateOf(showAlert)
    }
    if (openAlert) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = { openAlert = false },
            buttons = { Text(text = stringResource(id = R.string.alert_error_button)) },
            title = { Text(text = stringResource(id = R.string.alert_error_title)) },
            text = { Text(text = stringResource(id = R.string.alert_error_message)) }
        )
    }
}