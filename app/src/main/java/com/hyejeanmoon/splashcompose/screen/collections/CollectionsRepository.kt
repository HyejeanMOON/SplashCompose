package com.hyejeanmoon.splashcompose.screen.collections

import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo

interface CollectionsRepository {

    suspend fun getCollections(
        page: Int,
        perPage: Int
    ): List<Collections>

    suspend fun getPhotosOfCollection(
        id: String,
        page: Int,
        perPage: Int
    ): List<Photo>
}