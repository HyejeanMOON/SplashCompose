package com.hyejeanmoon.splashcompose.screen.collectionphotos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsRepository

class PhotosOfCollectionDataSource(
    private val collectionsRepository: CollectionsRepository,
    private val id: String
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: START_INDEX

        return try {
            val photos = collectionsRepository.getPhotosOfCollection(
                id = id,
                page = position,
                perPage = params.loadSize
            )
            LoadResult.Page(
                photos,
                if (position <= START_INDEX || photos.size < params.loadSize) null else position - 1,
                if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(
                exception
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return START_INDEX
    }

    companion object {
        private const val START_INDEX = 0
    }

}