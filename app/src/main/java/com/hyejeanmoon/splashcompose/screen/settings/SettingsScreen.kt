package com.hyejeanmoon.splashcompose.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hyejeanmoon.splashcompose.R

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
                SettingsItemDetail(itemName = s.title, itemContent = s.content) {
                    onSettingsItemClick(s.title)
                }
                if (index == it.itemDetail.size - 1) {
                    Spacer(modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .size(30.dp))
                }
            }
        }
    }
}

data class SettingsItem(
    val title: String,
    val itemDetail: List<SettingItemDetail>
)

data class SettingItemDetail(
    val title: String,
    val content: String = ""
)

@Composable
fun SettingsItemTitle(
    modifier: Modifier = Modifier,
    titleName: String
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(60.dp)
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
    itemContent: String = "",
    onItemClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(50.dp)
            .clickable { onItemClick(itemName) }
    ) {
        val (text, icon, content, divider) = createRefs()
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

        if (itemContent.isNotBlank()) {
            Text(
                modifier = Modifier
                    .constrainAs(content) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(0.dp, 0.dp, 20.dp, 0.dp),
                text = itemContent,
                fontSize = 14.sp,
                color = Color.Gray
            )
        } else {
            Image(
                modifier = Modifier
                    .size(34.dp)
                    .constrainAs(icon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(0.dp, 0.dp, 20.dp, 0.dp),
                painter = painterResource(id = R.drawable.ic_right),
                contentDescription = "right icon"
            )
        }

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
        SettingsItemDetail(itemName = "Version", itemContent = "1.0") {

        }
        SettingsItemDetail(itemName = "About me") {

        }
    }
}