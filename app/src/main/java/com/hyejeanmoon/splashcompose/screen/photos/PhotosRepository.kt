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

package com.hyejeanmoon.splashcompose.screen.photos

import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PhotosRepository(
    private val photosApiService: PhotosApiService,
    val orderBy: String
) {

    suspend fun getPhoto(id: String): Photo = suspendCoroutine {
        photosApiService.getPhoto(id).enqueue(ApiEnqueueCallback({ response ->
            it.resume(response)
        }, { exception ->
            it.resumeWithException(exception)
        }))
    }

    suspend fun getPhotoList(
        page:Int,
        perPage:Int
    ):List<Photo> = suspendCoroutine{
        photosApiService.getPhotos(page, perPage, orderBy).enqueue(
            ApiEnqueueCallback({ response ->
                it.resume(response)
            }, { exception ->
                it.resumeWithException(exception)

            })
        )
    }

    suspend fun getRandomPhoto(): Photo = suspendCoroutine {

        photosApiService.getRandomPhoto()
            .enqueue(ApiEnqueueCallback({ response ->
                it.resume(response)
            }, { exception ->
                it.resumeWithException(exception)
            }))
    }

    suspend fun getRandomPhotos(
        collections: String,
        featured: String,
        userName: String,
        query: String,
        orientation: String,
        count: String
    ): List<Photo> = suspendCoroutine {

        photosApiService.getRandomPhotos(collections, featured, userName, query, orientation, count)
            .enqueue(ApiEnqueueCallback({ response ->
                it.resume(response)
            }, { exception ->
                it.resumeWithException(exception)
            }))
    }
}