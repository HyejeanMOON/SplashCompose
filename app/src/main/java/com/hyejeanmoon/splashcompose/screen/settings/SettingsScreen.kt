/*
 * Copyright (C) 2021 HyejeanMOON.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyejeanmoon.splashcompose.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hyejeanmoon.splashcompose.MainActivity
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.ui.theme.MoonGray

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
                SettingsItemDetail(
                    itemName = s.title,
                    itemContent = s.content,
                    itemDescription = s.description
                ) {
                    onSettingsItemClick(s.title)
                }
                if (index == it.itemDetail.size - 1) {
                    Spacer(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .size(30.dp)
                    )
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
    val description: String = "",
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
    itemDescription: String = "",
    itemContent: String = "",
    onItemClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(50.dp)
            .clickable { onItemClick(itemName) }
    ) {
        val (text, icon, desc, content, divider) = createRefs()
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

        if (itemDescription.isNotBlank()) {
            Text(
                modifier = Modifier
                    .constrainAs(desc) {
                        start.linkTo(parent.start)
                        top.linkTo(text.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(20.dp, 0.dp, 0.dp, 5.dp),
                text = itemDescription,
                fontSize = 10.sp,
                color = Color.Gray
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

@Composable
fun SettingsAlertDialog(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel,
    item: String,
    onCloseDialog: () -> Unit
) {
    AlertDialog(
        modifier = modifier.padding(40.dp, 0.dp),
        onDismissRequest = {
            onCloseDialog()
        },
        confirmButton = {
            Button(
                onClick = {
                    onCloseDialog()
                }
            ) {
                Text(
                    text = "Dismiss",
                    color = Color.Black,
                )
            }
        },
        title = {
            Text(
                text = item,
                color = Color.Black
            )
        },
        text = {
            RadioButtonList(
                settingsViewModel = settingsViewModel,
                item = item
            )
        }
    )
}

@Composable
fun RadioButtonList(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel,
    item: String
) {
    var radioOptionList: List<String> = listOf()
    var initialPosition = 0

    when (item) {
        MainActivity.SETTINGS_ITEM_PHOTO_DISPLAY_ORDER -> {
            radioOptionList = settingsViewModel.orderByList
            initialPosition = settingsViewModel.getOrderByPosition()
        }
        MainActivity.SETTINGS_ITEM_DISPLAY_RESOLUTION -> {
            radioOptionList = settingsViewModel.displayResolutionList
            initialPosition = settingsViewModel.getDisplayResolutionPosition()
        }
    }

    var selectedOption = remember { mutableStateOf(initialPosition) }
    Column(
        modifier = modifier
    ) {
        radioOptionList.forEachIndexed { index, text ->
            Row(
                Modifier
                    .selectable(
                        selected = (text == radioOptionList[selectedOption.value]),
                        onClick = {
                            selectedOption.value = index
                            when (item) {
                                MainActivity.SETTINGS_ITEM_PHOTO_DISPLAY_ORDER -> {
                                    settingsViewModel.putOrderBy(text)
                                }
                                MainActivity.SETTINGS_ITEM_DISPLAY_RESOLUTION -> {
                                    settingsViewModel.putDisplayResolution(text)
                                }
                            }
                        }
                    )
                    .padding(horizontal = 20.dp)
            ) {
                RadioButton(
                    selected = (text == radioOptionList[selectedOption.value]),
                    onClick = {
                        selectedOption.value = index
                        when (item) {
                            MainActivity.SETTINGS_ITEM_PHOTO_DISPLAY_ORDER -> {
                                settingsViewModel.putOrderBy(text)
                            }
                            MainActivity.SETTINGS_ITEM_DISPLAY_RESOLUTION -> {
                                settingsViewModel.putDisplayResolution(text)
                            }
                        }
                    }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
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