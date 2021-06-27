package com.hyejeanmoon.splashcompose.screen.photodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.OkHttpClient
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class PhotoDetailViewModel(
    private val state: SavedStateHandle
) : ViewModel() {
    private var photoId = ""

    init {
        photoId = state.get<String>(PhotoDetailActivity.INTENT_PHOTO_ID).orEmpty()
    }

    val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val photosApiService =
        ApiServiceHelper.createPhotosApiService(
            EnvParameters.BASE_URL,
            OkHttpClient().splashOkHttpClient
        )

    private val _photo: MutableLiveData<Photo> = MutableLiveData()
    val photo: LiveData<Photo> = _photo

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> = _exception

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

    fun downloadPhotoById(id: String) {

    }
}