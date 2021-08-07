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

package com.hyejeanmoon.splashcompose.screen.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hyejeanmoon.splashcompose.db.AppDatabase
import com.hyejeanmoon.splashcompose.db.FavoritePhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel(
    app: Application
) : AndroidViewModel(app) {

    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val database = AppDatabase.getInstance(app)

    private val _favoritePhotoList: MutableLiveData<List<FavoritePhoto>> = MutableLiveData()
    val favoritePhotoList: LiveData<List<FavoritePhoto>> get() = _favoritePhotoList

    fun getAllFavoritePhotos() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            _favoritePhotoList.postValue(
                database.getUserDao().getAllFavoritePhoto()
            )
        }
        isRefreshing.value = false
    }
}