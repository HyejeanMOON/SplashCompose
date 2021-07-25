package com.hyejeanmoon.splashcompose.screen.webview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hyejeanmoon.splashcompose.R
import com.hyejeanmoon.splashcompose.SetUpStatusBar
import com.hyejeanmoon.splashcompose.ui.theme.SplashComposeTheme

class WebViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra(WEB_VIEW_URL)
        val title = intent.getStringExtra(WEB_VIEW_TITLE)

        setContent {
            SetUpStatusBar()
            SplashComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = title.orEmpty(),
                                        color = Color.Black
                                    )
                                },
                                navigationIcon = {
                                    Icon(
                                        modifier = Modifier.clickable { finish() },
                                        painter = painterResource(id = R.drawable.ic_arrow_back),
                                        contentDescription = "back icon",
                                        tint = Color.Black
                                    )
                                }
                            )
                        }
                    ) {
                        WebPageScreen(urlToRender = url.orEmpty())
                    }
                }
            }
        }
    }

    companion object {
        private const val WEB_VIEW_URL = "WEB_VIEW_URL"
        private const val WEB_VIEW_TITLE = "WEB_VIEW_TITLE"

        fun start(url: String, title: String, activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, WebViewActivity::class.java)
            intent.putExtra(WEB_VIEW_URL, url)
            intent.putExtra(WEB_VIEW_TITLE, title)
            activity.startActivity(intent)
        }
    }
}