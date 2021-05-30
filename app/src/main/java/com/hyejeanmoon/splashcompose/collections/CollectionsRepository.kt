package com.hyejeanmoon.splashcompose.collections

import com.hyejeanmoon.splashcompose.api.ApiEnqueueCallback
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectionsRepository(
    private val collectionsApiService: CollectionsApiService
) {
    suspend fun getCollections(
        page: Int,
        perPage: Int
    ): List<Collections> = suspendCoroutine {
        collectionsApiService.getCollections(
            page,
            perPage
        ).enqueue(
            ApiEnqueueCallback({ response ->
                it.resume(response)
            }, { exception ->
                it.resumeWithException(exception)
            })
        )
    }

    suspend fun getPhotosOfCollection(
        id: String,
        page: Int,
        perPage: Int
    ): List<Photo> = suspendCoroutine {
        collectionsApiService.getPhotosOfCollection(
            id,
            page,
            perPage
        ).enqueue(
            ApiEnqueueCallback({ response ->
                it.resume(response)
            }, { exception ->
                it.resumeWithException(exception)
            })
        )
    }
}