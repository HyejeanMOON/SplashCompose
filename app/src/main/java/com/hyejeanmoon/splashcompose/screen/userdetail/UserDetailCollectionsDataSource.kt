package com.hyejeanmoon.splashcompose.screen.userdetail

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.Collections

class UserDetailCollectionsDataSource(
    private val userDetailRepository: UserDetailRepository,
    private val userName: String,
    private val orderBy: String
) : PagingSource<Int, Collections>() {

    override fun getRefreshKey(state: PagingState<Int, Collections>): Int? {
        return START_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collections> {
        val position = params.key ?: START_INDEX

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