package com.hyejeanmoon.splashcompose.screen.collections

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.Collections

class CollectionsDataSource(
    private val collectionsRepository: CollectionsRepository
) : PagingSource<Int, Collections>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collections> {
        val position = params.key ?: START_INDEX

        return try {
            val collections = collectionsRepository.getCollections(
                page = position,
                perPage = params.loadSize
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

    override fun getRefreshKey(state: PagingState<Int, Collections>): Int? {
        return START_INDEX
    }

    companion object {
        private const val START_INDEX = 0
    }
}