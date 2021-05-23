package com.hyejeanmoon.splashcompose.photo

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.OkHttpClient
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import kotlinx.coroutines.flow.Flow

class PhotoViewModel : ViewModel() {

    private val photosApiService =
        ApiServiceHelper.createPhotosApiService(
            EnvParameters.BASE_URL,
            OkHttpClient().splashOkHttpClient
        )

    private val photosDataSource = PhotosDataSource(PhotosRepository(photosApiService))

    var photoList = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 30
        ),
        pagingSourceFactory = {photosDataSource}
    ).flow
}