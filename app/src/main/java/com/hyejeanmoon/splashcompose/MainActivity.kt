package com.hyejeanmoon.splashcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme
import com.hyejeanmoon.splashcompose.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hyejeanmoon.splashcompose.photo.PhotoViewModel
import com.hyejeanmoon.splashcompose.ui.theme.Pewter
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashComposeApp(viewModel)
        }
    }
}

@Composable
fun SplashComposeApp(viewModel: PhotoViewModel) {
    SplashComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            Scaffold(
                modifier = Modifier,
                bottomBar = {
                    BottomNavigation {
                        val screenList = listOf(
                            Screen.Photo,
                            Screen.Collections,
                            Screen.Love,
                            Screen.Settings,
                        )
//                        val navBackStackEntry by navController.currentBackStackEntryAsState()
//                        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)

                        screenList.forEach { screen ->
                            BottomNavigationItem(
                                selected = true,
                                onClick = { /*TODO*/ },
                                icon = {
                                    Icon(
                                        modifier = Modifier.size(28.dp),
                                        painter = painterResource(id = screen.drawableId),
                                        contentDescription = stringResource(
                                            id = screen.stringId
                                        )
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(id = screen.stringId),
                                        color = Color.Black
                                    )
                                }
                            )
                        }
                    }
                },
                topBar = {
                    TopAppBar(
                        title = { Text(text = "SplashCompose", color = Color.Black) }
                    )
                }
            ) {
//                NavHost(navController, startDestination = Screen.Photo.route) {
//                    composable(Screen.Photo.route) {
//                        Text(text = "hello")
//                    }
//                    composable(Screen.Collections.route) { }
//                    composable(Screen.Love.route) {}
//                    composable(Screen.Settings.route) {}
//                }

                TestTextView(viewModel = viewModel)

            }
        }
    }
}

@Composable
fun TestTextView(
    viewModel: PhotoViewModel
) {
    val pagingItems = viewModel.photoList.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(lazyPagingItems = pagingItems){ item ->
            Text(text = item?.id.toString(), modifier = Modifier.size(120.dp))
        }
    }
}
