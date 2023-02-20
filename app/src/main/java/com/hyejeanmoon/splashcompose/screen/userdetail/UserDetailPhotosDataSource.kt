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

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.UsersPhotos

class UserDetailPhotosDataSource(
    private val userDetailRepository: UserDetailRepository,
    private val userName: String
) : PagingSource<Int, UsersPhotos>() {

    override fun getRefreshKey(state: PagingState<Int, UsersPhotos>): Int? {
        return START_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersPhotos> {
        val position = params.key ?: START_INDEX

        return try {
            val photos = userDetailRepository.getUsersPhotos(
                userName,
                page = position,
                perPage = params.loadSize,
                orderBy = "latest"
            )

            LoadResult.Page(
                photos,
                if (position <= START_INDEX || photos.size < params.loadSize) null else position - 1,
                if (photos.isEmpty() || photos.size < params.loadSize) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(
                exception
            )
        }
    }

    companion object {
        private const val START_INDEX = 1
    }
}