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

package com.hyejeanmoon.splashcompose.screen.collectionphotos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.SplashOkHttpClient
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsRepository
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import com.hyejeanmoon.splashcompose.utils.SharedPreferencesUtils
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PhotosOfCollectionViewModel(
    app:Application,
    @Assisted private val state: SavedStateHandle
):AndroidViewModel(app) {

    private var id = ""

    init {
        id = state.get<String>(PhotosOfCollectionActivity.COLLECTION_ID).orEmpty()
    }

    private val pref = SharedPreferencesUtils(app)
    val resolution = pref.getString(SharedPreferencesUtils.KEY_DISPLAY_RESOLUTION)

    private val collectionsApiService =
        ApiServiceHelper.createCollectionsApiService(
            EnvParameters.BASE_URL,
            SplashOkHttpClient().splashOkHttpClient
        )

    private val collectionRepository = CollectionsRepository(collectionsApiService)

    private val photosOfCollectionDataSource =
        PhotosOfCollectionDataSource(collectionRepository,id)

    var photosOfCollections = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = false,
            initialLoadSize = 30
        ),
        pagingSourceFactory = { photosOfCollectionDataSource }
    ).flow.cachedIn(viewModelScope)
}