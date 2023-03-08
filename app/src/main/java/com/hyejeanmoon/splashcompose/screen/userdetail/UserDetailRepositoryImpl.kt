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

package com.hyejeanmoon.splashcompose.screen.userdetail

import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.UsersPhotos
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserDetailRepositoryImpl @Inject constructor(
    private val userDetailApiService: UserDetailApiService
) : UserDetailRepository {

    override suspend fun getUsersPhotos(
        userName: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): List<UsersPhotos> = suspendCoroutine {
        userDetailApiService.getPhotosByUserName(
            userName,
            page,
            perPage,
            orderBy
        ).enqueue(ApiEnqueueCallback({ photos ->
            it.resume(photos)
        }, { exception ->
            it.resumeWithException(exception = exception)
        }))
    }

    override suspend fun getUsersLikedPhotos(
        userName: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): List<UsersPhotos> = suspendCoroutine {
        userDetailApiService.getLikedPhotosByUserName(
            userName,
            page,
            perPage,
            orderBy
        ).enqueue(ApiEnqueueCallback({ photos ->
            it.resume(photos)
        }, { exception ->
            it.resumeWithException(exception = exception)
        }))
    }

    override suspend fun getUsersCollections(
        userName: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): List<Collections> = suspendCoroutine {
        userDetailApiService.getCollectionsByUserName(
            userName,
            page,
            perPage,
            orderBy
        ).enqueue(ApiEnqueueCallback({ collections ->
            it.resume(collections)
        }, { exception ->
            it.resumeWithException(exception = exception)
        }))
    }

    companion object {
        private const val ORDER_BY_LATEST = "latest"
    }
}