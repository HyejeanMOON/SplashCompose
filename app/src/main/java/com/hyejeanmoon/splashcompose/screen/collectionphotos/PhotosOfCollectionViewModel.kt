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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hyejeanmoon.splashcompose.entity.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PhotosOfCollectionViewModel @Inject constructor(
    app: Application,
    photosOfCollectionDataSource: PhotosOfCollectionDataSource
) : AndroidViewModel(app) {

    private val photosOfCollections by lazy {
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { photosOfCollectionDataSource }
        ).flow.cachedIn(viewModelScope)
    }

    var viewStates by mutableStateOf(
        PhotosOfCollectionViewState(
            pagingPhotos = photosOfCollections
        )
    )

    companion object {
        private const val PAGE_SIZE = 15
        private const val INITIAL_LOAD_SIZE = 15
    }
}

data class PhotosOfCollectionViewState(
    val pagingPhotos: PagingPhotosOfCollections
)

typealias PagingPhotosOfCollections = Flow<PagingData<Photo>>