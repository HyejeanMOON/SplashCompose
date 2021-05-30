package com.hyejeanmoon.splashcompose.collectionphotos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.collections.CollectionsRepository
import com.hyejeanmoon.splashcompose.entity.Photo

class PhotosOfCollectionDataSource(
    private val collectionsRepository: CollectionsRepository,
    private val id: String
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: START_INDEX

        val photos = collectionsRepository.getPhotosOfCollection(
            id = id,
            page = position,
            perPage = params.loadSize
        )
        return LoadResult.Page(
            photos,
            if (position <= START_INDEX) null else position - 1,
            if (photos.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return START_INDEX
    }

    companion object {
        private const val START_INDEX = 0
    }

}