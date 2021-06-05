package com.hyejeanmoon.splashcompose.screen.collections

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.Collections

class CollectionsDataSource(
    private val collectionsRepository: CollectionsRepository
): PagingSource<Int, Collections>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collections> {
        val position = params.key ?: START_INDEX

        val collections = collectionsRepository.getCollections(
            page = position,
            perPage = params.loadSize
        )
        return LoadResult.Page(
            collections,
            if (position <= START_INDEX) null else position - 1,
            if (collections.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Collections>): Int? {
        return START_INDEX
    }

    companion object {
        private const val START_INDEX = 0
    }
}