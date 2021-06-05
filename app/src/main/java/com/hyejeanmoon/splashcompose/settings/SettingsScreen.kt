package com.hyejeanmoon.splashcompose.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hyejeanmoon.splashcompose.compose.SettingsItemDetail
import com.hyejeanmoon.splashcompose.compose.SettingsItemTitle

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsItems: List<SettingsItem>,
    onSettingsItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = settingsItems) {
            SettingsItemTitle(titleName = it.title)
            it.itemDetail.forEachIndexed { index, s ->
                SettingsItemDetail(itemName = s) {
                    onSettingsItemClick(s)
                }
                if (index == it.itemDetail.size-1) {
                    Spacer(modifier = Modifier.background(Color.White).fillMaxWidth().size(30.dp))
                }
            }
        }
    }
}

data class SettingsItem(
    val title: String,
    val itemDetail: List<String>
)