package com.hyejeanmoon.splashcompose.screen.userdetail

import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.UsersPhotos
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserDetailRepository(
    private val userDetailApiService: UserDetailApiService
) {

    suspend fun getUsersPhotos(
        userName: String,
        page:Int,
        perPage:Int
    ): List<UsersPhotos> = suspendCoroutine {
        userDetailApiService.getPhotosByUserName(
            userName,
            page,
            perPage,
            ORDER_BY_LATEST
        ).enqueue(ApiEnqueueCallback({ photos ->
            it.resume(photos)
        }, { exception ->
            it.resumeWithException(exception = exception)
        }))
    }

    suspend fun getUsersLikedPhotos(
        userName: String,
        page:Int,
        perPage:Int
    ): List<UsersPhotos> = suspendCoroutine {
        userDetailApiService.getLikedPhotosByUserName(
            userName,
            page,
            perPage,
            ORDER_BY_LATEST
        ).enqueue(ApiEnqueueCallback({ photos ->
            it.resume(photos)
        }, { exception ->
            it.resumeWithException(exception = exception)
        }))
    }

    suspend fun getUsersCollections(
        userName: String,
        page:Int,
        perPage:Int
    ): List<Collections> = suspendCoroutine {
        userDetailApiService.getCollectionsByUserName(
            userName,
            page,
            perPage,
            ORDER_BY_LATEST
        ).enqueue(ApiEnqueueCallback({ collections ->
            it.resume(collections)
        }, { exception ->
            it.resumeWithException(exception = exception)
        }))
    }

    companion object{
        private const val ORDER_BY_LATEST = "latest"
    }
}