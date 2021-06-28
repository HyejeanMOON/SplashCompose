package com.hyejeanmoon.splashcompose.screen.photos

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.OkHttpClient
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PhotosViewModel : ViewModel() {

    private val photosApiService =
        ApiServiceHelper.createPhotosApiService(
            EnvParameters.BASE_URL,
            OkHttpClient().splashOkHttpClient
        )

    private val photosDataSource = PhotosDataSource(PhotosRepository(photosApiService))

    var photoList = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        ),
        pagingSourceFactory = { photosDataSource }
    ).flow
}