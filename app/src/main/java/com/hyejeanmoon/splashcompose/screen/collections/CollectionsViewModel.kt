package com.hyejeanmoon.splashcompose.screen.collections

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.OkHttpClient
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CollectionsViewModel : ViewModel() {

    private val collectionsApiService =
        ApiServiceHelper.createCollectionsApiService(
            EnvParameters.BASE_URL,
            OkHttpClient().splashOkHttpClient
        )

    private val collectionRepository = CollectionsRepository(collectionsApiService)

    private val collectionsDataSource =
        CollectionsDataSource(collectionRepository)

    var collections = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            initialLoadSize = 30
        ),
        pagingSourceFactory = { collectionsDataSource }
    ).flow
}