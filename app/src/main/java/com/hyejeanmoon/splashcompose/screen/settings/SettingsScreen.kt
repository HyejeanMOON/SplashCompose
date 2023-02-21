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

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.Screen
import com.hyejeanmoon.splashcompose.utils.DataManager
import com.hyejeanmoon.splashcompose.utils.NavUtils

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    navController:NavHostController
) {
    val settingsItemList = listOf(
        SettingsItem(
            SettingsTitles.APPLICATION_SETTINGS,
            listOf(
                SettingItemDetail(
                    title = SettingsItems.DISPLAY_RESOLUTION
                ),
                SettingItemDetail(
                    title = SettingsItems.PHOTO_DISPLAY_ORDER,
                    "This setting will take effect the next time you start the app"
                ),
                SettingItemDetail(title = SettingsItems.CLEAR_CACHE)
            )
        ),
        SettingsItem(
            SettingsTitles.OTHERS,
            listOf(
                SettingItemDetail(
                    title = SettingsItems.VERSION,
                    content = "1.4.0"
                ),
                SettingItemDetail(
                    title = SettingsItems.LICENSES
                ),
                SettingItemDetail(title = SettingsItems.ABOUT_DEVELOPER)
            )
        )
    )

    SettingsScreenUI(
        modifier = modifier,
        viewModel = viewModel,
        settingsItems = settingsItemList,
        navController = navController
    )
}

@Composable
fun SettingsScreenUI(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    navController: NavHostController,
    settingsItems: List<SettingsItem>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(items = settingsItems) {
            SettingsItemTitle(titleName = it.title.title)
            it.itemDetail.forEachIndexed { index, s ->
                SettingsItemDetail(
                    item = s.title,
                    viewModel = viewModel,
                    navController = navController,
                    itemContent = s.content,
                    itemDescription = s.description
                )
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
    val title: SettingsTitles,
    val itemDetail: List<SettingItemDetail>
)

data class SettingItemDetail(
    val title: SettingsItems,
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
    viewModel: SettingsViewModel,
    navController: NavHostController,
    item: SettingsItems,
    itemDescription: String = "",
    itemContent: String = ""
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(50.dp)
            .clickable {
                when (item) {
                    SettingsItems.DISPLAY_RESOLUTION, SettingsItems.PHOTO_DISPLAY_ORDER -> {
                        openDialog = true
                    }
                    SettingsItems.VERSION -> {
                        // do nothing
                    }
                    SettingsItems.LICENSES -> {
                        NavUtils.navTo(navController,Screen.License)
                    }
                    SettingsItems.ABOUT_DEVELOPER -> {
                        val uri =
                            Uri.parse("https://github.com/HyejeanMOON")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    }
                    SettingsItems.CLEAR_CACHE -> {
                        DataManager.clearCacheInScopedStorage(context)
                    }
                }
            }
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
            text = item.title,
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

        if (openDialog) {
            SettingsAlertDialog(
                settingsViewModel = viewModel,
                item = item
            ) {
                openDialog = false
            }
        }
    }
}

@Composable
fun SettingsAlertDialog(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel,
    item: SettingsItems,
    onCloseDialog: () -> Unit
) {
    AlertDialog(
        modifier = modifier.padding(40.dp, 0.dp),
        onDismissRequest = {
            // do nothing
        },
        confirmButton = {
            Text(
                text = "Dismiss",
                modifier = Modifier
                    .padding(10.dp, 5.dp)
                    .clickable {
                    onCloseDialog()
                },
                color = Color.Black,
            )

        },
        title = {
            Text(
                text = item.title,
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
    item: SettingsItems
) {
    var radioOptionList: List<String> = listOf()
    var initialPosition = 0

    when (item) {
        SettingsItems.PHOTO_DISPLAY_ORDER -> {
            radioOptionList = settingsViewModel.orderByList
            initialPosition = settingsViewModel.getOrderByPosition()
        }
        SettingsItems.DISPLAY_RESOLUTION -> {
            radioOptionList = settingsViewModel.displayResolutionList
            initialPosition = settingsViewModel.getDisplayResolutionPosition()
        }
        else -> {
            // do noting
        }
    }

    var selectedOption by remember { mutableStateOf(initialPosition) }
    Column(
        modifier = modifier
    ) {
        radioOptionList.forEachIndexed { index, text ->
            ConstraintLayout(
                Modifier
                    .selectable(
                        selected = (text == radioOptionList[selectedOption]),
                        onClick = {
                            selectedOption = index
                            when (item) {
                                SettingsItems.PHOTO_DISPLAY_ORDER -> {
                                    settingsViewModel.putOrderBy(text)
                                }
                                SettingsItems.DISPLAY_RESOLUTION -> {
                                    settingsViewModel.putDisplayResolution(text)
                                }
                                else -> {
                                    // do nothing
                                }
                            }
                        }
                    )
                    .padding(horizontal = 20.dp)
            ) {
                val (radioRef,textRef) = createRefs()

                RadioButton(
                    modifier = Modifier.constrainAs(radioRef){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                    selected = (text == radioOptionList[selectedOption]),
                    onClick = {
                        selectedOption = index
                        when (item) {
                            SettingsItems.PHOTO_DISPLAY_ORDER -> {
                                settingsViewModel.putOrderBy(text)
                            }
                            SettingsItems.DISPLAY_RESOLUTION -> {
                                settingsViewModel.putDisplayResolution(text)
                            }
                            else -> {
                                // do nothing
                            }
                        }
                    }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                        .constrainAs(textRef){
                            top.linkTo(radioRef.top)
                            bottom.linkTo(radioRef.bottom)
                            start.linkTo(radioRef.end)
                        }
                )
            }
        }
    }
}