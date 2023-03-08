package com.hyejeanmoon.splashcompose.screen.userdetail

import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.UsersPhotos

interface UserDetailRepository {

    suspend fun getUsersPhotos(
        userName: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): List<UsersPhotos>

    suspend fun getUsersLikedPhotos(
        userName: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): List<UsersPhotos>

    suspend fun getUsersCollections(
        userName: String,
        page: Int,
        perPage: Int,
        orderBy: String
    ): List<Collections>
}