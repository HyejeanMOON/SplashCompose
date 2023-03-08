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

import android.content.SharedPreferences
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.EnvParameters
import com.hyejeanmoon.splashcompose.utils.getString
import javax.inject.Inject

class UserDetailCollectionsDataSource @Inject constructor(
    private val userDetailRepository: UserDetailRepository,
    private val sharedPreferences: SharedPreferences
) : PagingSource<Int, Collections>() {

    override fun getRefreshKey(state: PagingState<Int, Collections>): Int? {
        return START_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collections> {
        val position = params.key ?: START_INDEX
        val userName = sharedPreferences.getString(
            EnvParameters.KEY_PHOTO_USER_NAME
        )
        val orderBy = sharedPreferences.getString(
            EnvParameters.KEY_ORDER_BY
        )

        return try {
            val collections = userDetailRepository.getUsersCollections(
                userName,
                page = position,
                perPage = params.loadSize,
                orderBy = orderBy
            )

            LoadResult.Page(
                collections,
                if (position <= START_INDEX || collections.size < params.loadSize) null else position - 1,
                if (collections.isEmpty() || collections.size < params.loadSize) null else position + 1
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