package com.hyejeanmoon.splashcompose.screen.random

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.SplashOkHttpClient
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import kotlinx.coroutines.flow.MutableStateFlow

class RandomPhotoViewModel(
    val app: Application
) : AndroidViewModel(app) {

    private val photosApiService =
        ApiServiceHelper.createPhotosApiService(
            EnvParameters.BASE_URL,
            SplashOkHttpClient().splashOkHttpClient
        )

    private val _randomPhoto: MutableLiveData<Photo> = MutableLiveData()
    val randomPhoto: LiveData<Photo> get() = _randomPhoto

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> get() = _exception

    fun getRandomPhoto() {
        photosApiService.getRandomPhoto().enqueue(
            ApiEnqueueCallback({
                _randomPhoto.postValue(it)
            }, {
                _exception.postValue(it)
            })
        )
    }
}