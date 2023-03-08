package com.hyejeanmoon.splashcompose.screen.photos

import com.hyejeanmoon.splashcompose.entity.Photo

interface PhotosRepository {

    suspend fun getPhoto(id: String): Photo

    suspend fun getPhotoList(
        page: Int,
        perPage: Int
    ): List<Photo>

    suspend fun getRandomPhoto(): Photo

    suspend fun getRandomPhotos(
        collections: String,
        featured: String,
        userName: String,
        query: String,
        orientation: String,
        count: String
    ): List<Photo>
}