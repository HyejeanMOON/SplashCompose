package com.hyejeanmoon.splashcompose.screen.favorite

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
class FavoriteViewModel(
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