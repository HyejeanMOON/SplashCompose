package com.hyejeanmoon.splashcompose.screen.userdetail

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import com.hyejeanmoon.splashcompose.api.ApiServiceHelper
import com.hyejeanmoon.splashcompose.api.OkHttpClient
import com.hyejeanmoon.splashcompose.entity.UserDetail
import com.hyejeanmoon.splashcompose.entity.UsersPhotos
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import com.hyejeanmoon.splashcompose.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class UserDetailViewModel(
    app: Application,
    private val state: SavedStateHandle
) : AndroidViewModel(app) {

    private var userName: String = ""

    init {
        userName = state.get<String>(UserDetailActivity.INTENT_USER_NAME).orEmpty()
    }

    private val _userDetail: MutableLiveData<UserDetail> = MutableLiveData()
    val userDetail: LiveData<UserDetail> get() = _userDetail

    private val userDetailApiService = ApiServiceHelper.createUserDetailApiService(
        EnvParameters.BASE_URL,
        OkHttpClient().splashOkHttpClient
    )

    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    val exception: LiveData<Exception> get() = _exception

    private val userDetailRepository = UserDetailRepository(userDetailApiService, getOrderBy())
    private val userDetailPhotosDataSource = UserDetailPhotosDataSource(
        userDetailRepository,
        userName
    )
    private val userDetailLikedPhotosDataSource = UserDetailLikedPhotosDataSource(
        userDetailRepository,
        userName
    )
    private val userDetailCollectionsDataSource = UserDetailCollectionsDataSource(
        userDetailRepository,
        userName
    )

    private val pref = SharedPreferencesUtils(app)

    fun getDisplayResolution(): String {
        return pref.getString(SharedPreferencesUtils.KEY_DISPLAY_RESOLUTION)
    }

    private fun getOrderBy(): String {
        return pref.getString(SharedPreferencesUtils.KEY_ORDER_BY)
    }

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

    val userDetailPhotosFlow = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        ),
        pagingSourceFactory = { userDetailPhotosDataSource }
    ).flow

    val userDetailCollectionsFlow = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        ),
        pagingSourceFactory = { userDetailCollectionsDataSource }
    ).flow

    val userDetailLikedPhotosDataSourceFlow = Pager(
        config = PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        ),
        pagingSourceFactory = { userDetailLikedPhotosDataSource }
    ).flow
}