package com.hyejeanmoon.splashcompose.photo

import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PhotosRepository(
    private val photosApiService: PhotosApiService
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
        perPage:Int,
        orderBy:String
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