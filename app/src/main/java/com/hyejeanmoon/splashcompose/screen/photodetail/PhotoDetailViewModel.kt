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

class PhotoDetailViewModel(
    private val state: SavedStateHandle
) : ViewModel() {
    private var photoId = ""

    init {
        photoId = state.get<String>(PhotoDetailActivity.INTENT_PHOTO_ID).orEmpty()
    }

    private val photosApiService =
        ApiServiceHelper.createPhotosApiService(
            EnvParameters.BASE_URL,
            OkHttpClient().splashOkHttpClient
        )

    private val _photo: MutableLiveData<Photo> = MutableLiveData()
    val photo: LiveData<Photo> = _photo

    fun getPhotoById() {
        if (photoId.isNotBlank()) {
            photosApiService.getPhoto(id = photoId).enqueue(
                ApiEnqueueCallback({
                    _photo.value = it
                }, {
                    // handle exception
                })
            )
        }
    }

    fun downloadPhotoById(id: String) {

    }
}