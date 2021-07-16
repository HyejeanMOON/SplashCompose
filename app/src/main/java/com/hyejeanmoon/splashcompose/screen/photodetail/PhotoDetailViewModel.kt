package com.hyejeanmoon.splashcompose.screen.photodetail

import android.app.Application
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.OkHttpClient
import com.hyejeanmoon.splashcompose.db.AppDatabase
import com.hyejeanmoon.splashcompose.db.FavoritePhoto
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import com.hyejeanmoon.splashcompose.utils.FileUtils
import com.hyejeanmoon.splashcompose.utils.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PhotoDetailViewModel(
    val app: Application,
    state: SavedStateHandle
) : AndroidViewModel(app) {
    private var photoId = ""

    init {
        photoId = state.get<String>(PhotoDetailActivity.INTENT_PHOTO_ID).orEmpty()
    }

    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val database = AppDatabase.getInstance(app)

    private val photosApiService =
        ApiServiceHelper.createPhotosApiService(
            EnvParameters.BASE_URL,
            OkHttpClient().splashOkHttpClient
        )

    private val _photo: MutableLiveData<Photo> = MutableLiveData()
    val photo: LiveData<Photo> get() = _photo

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> get() = _exception

    private val _isFavoritePhoto: MutableLiveData<Boolean> = MutableLiveData()
    val isFavoritePhoto: LiveData<Boolean> get() = _isFavoritePhoto

    fun getPhotoById() {
        isRefreshing.value = true
        if (photoId.isNotBlank()) {
            photosApiService.getPhoto(id = photoId).enqueue(
                ApiEnqueueCallback({
                    _photo.value = it
                    isRefreshing.value = false
                }, { exception ->
                    // handle exception
                    _exception.value = exception
                    isRefreshing.value = false
                })
            )
        }
    }

    fun isFavoritePhoto() {
        LogUtils.outputLog("isFavoritePhoto")
        viewModelScope.launch(Dispatchers.IO) {
            _isFavoritePhoto.postValue(database.getUserDao().isFavoritePhoto(photoId).isNotEmpty())
        }
    }

    fun favoritePhoto() {
        LogUtils.outputLog("favoritePhoto")
        viewModelScope.launch(Dispatchers.IO) {
            if (_isFavoritePhoto.value == true) {
                database.getUserDao().deleteFavoritePhoto(
                    FavoritePhoto(
                        id = _photo.value?.id.orEmpty(),
                        photoUrl = _photo.value?.urls?.small.orEmpty()
                    )
                )
            } else {
                database.getUserDao().insertFavoritePhoto(
                    FavoritePhoto(
                        id = _photo.value?.id.orEmpty(),
                        photoUrl = _photo.value?.urls?.small.orEmpty()
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun downloadPhotoByIdVersionQ() {
        if (photoId.isNotBlank()) {
            photosApiService.downloadPhoto(photoId).enqueue(
                ApiEnqueueCallback({
                    it.url?.also { url ->
                        saveImageFileVersionQ(url)
                    }
                }, {
                    _exception.value = it
                })
            )
        }
    }

    fun downloadPhotoById() {
        if (photoId.isNotBlank()) {
            photosApiService.downloadPhoto(photoId).enqueue(
                ApiEnqueueCallback({
                    it.url?.also { url ->
                        saveImageFile(url)
                    }
                }, {
                    _exception.value = it
                })
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageFileVersionQ(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = Glide
                .with(app)
                .asBitmap()
                .load(Uri.parse(url))
                .submit()
                .get()
            FileUtils.saveImageVersionQ(app, bitmap)
        }
    }

    private fun saveImageFile(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = Glide
                .with(app)
                .asBitmap()
                .load(Uri.parse(url))
                .submit()
                .get()
            FileUtils.saveImage(app, bitmap)
        }
    }
}