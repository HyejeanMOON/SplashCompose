package com.hyejeanmoon.splashcompose.screen.userdetail

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.Collections

class UserDetailCollectionsDataSource(
    private val userDetailRepository: UserDetailRepository,
    private val userName: String
) : PagingSource<Int, Collections>() {

    override fun getRefreshKey(state: PagingState<Int, Collections>): Int? {
        return START_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collections> {
        val position = params.key ?: START_INDEX

        val collections = userDetailRepository.getUsersCollections(
            userName,
            page = position,
            perPage = params.loadSize
        )

        return LoadResult.Page(
            collections,
            if (position <= START_INDEX) null else position - 1,
            if (collections.isEmpty()) null else position + 1
        )
    }

    companion object {
        private const val START_INDEX = 0
    }
}