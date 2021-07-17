package com.hyejeanmoon.splashcompose.screen.collections

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.SplashOkHttpClient
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import com.hyejeanmoon.splashcompose.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CollectionsViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val collectionsApiService =
        ApiServiceHelper.createCollectionsApiService(
            EnvParameters.BASE_URL,
            SplashOkHttpClient().splashOkHttpClient
        )

    private val collectionRepository = CollectionsRepository(collectionsApiService)

    private val pref = SharedPreferencesUtils(app)
    val resolution = pref.getString(SharedPreferencesUtils.KEY_DISPLAY_RESOLUTION)

    private val collectionsDataSource =
        CollectionsDataSource(collectionRepository)

    var collections = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        ),
        pagingSourceFactory = { collectionsDataSource }
    ).flow
}