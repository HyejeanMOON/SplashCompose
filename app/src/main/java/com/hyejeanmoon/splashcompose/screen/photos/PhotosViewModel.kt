package com.hyejeanmoon.splashcompose.screen.photos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.OkHttpClient
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import com.hyejeanmoon.splashcompose.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PhotosViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val photosApiService =
        ApiServiceHelper.createPhotosApiService(
            EnvParameters.BASE_URL,
            OkHttpClient().splashOkHttpClient
        )

    private val pref = SharedPreferencesUtils(app)
    val resolution = pref.getString(SharedPreferencesUtils.KEY_DISPLAY_RESOLUTION)
    val orderBy = pref.getString(SharedPreferencesUtils.KEY_ORDER_BY)

    private val photosDataSource =
        PhotosDataSource(PhotosRepository(photosApiService,orderBy))

    var photoList = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        ),
        pagingSourceFactory = { photosDataSource }
    ).flow
}