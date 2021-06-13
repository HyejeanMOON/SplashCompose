package com.hyejeanmoon.splashcompose.screen.userdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.OkHttpClient
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.entity.UserDetail
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class UserDetailViewModel(
    private val state: SavedStateHandle
) : ViewModel() {

    private var userName: String = ""

    init {
        userName = state.get<String>(UserDetailActivity.INTENT_USER_NAME).orEmpty()
    }

    private val _userDetail: MutableLiveData<UserDetail> = MutableLiveData()
    val userDetail: LiveData<UserDetail> get() = _userDetail

    private val _userPhotos: MutableLiveData<List<Photo>> = MutableLiveData()
    val userPhotos: LiveData<List<Photo>> get() = _userPhotos

    private val userDetailApiService = ApiServiceHelper.createUserDetailApiService(
        EnvParameters.BASE_URL,
        OkHttpClient().splashOkHttpClient
    )

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> get() = _exception

    fun getUserDetailInfo() {
        userDetailApiService.getUserDetails(
            userName = userName
        ).enqueue(ApiEnqueueCallback(
            {
                _userDetail.value = it
            }, {
                _exception.value = it
            })
        )
    }

    fun getUsersPhotos(){
        userDetailApiService.getPhotosByUserName(userName).enqueue(
            ApiEnqueueCallback({
                Log.d("MOON", "getUsersPhotos ${it.size}")
                 _userPhotos.value = it
            },{
                _exception.value = it
            })
        )

    }
}