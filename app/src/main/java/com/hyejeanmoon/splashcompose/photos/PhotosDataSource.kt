package com.hyejeanmoon.splashcompose.photos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.Photo

class PhotosDataSource(
    private val photosRepository: PhotosRepository
):PagingSource<Int,Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: START_INDEX

        val photoList = photosRepository.getPhotoList(
            page = position,
            perPage = params.loadSize,
            orderBy = ORDER_BY_LATEST
        )
        return LoadResult.Page(
            photoList,
            if (position <= START_INDEX) null else position - 1,
            if (photoList.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return START_INDEX
    }

    companion object {
        private const val START_INDEX = 0
        private const val ORDER_BY_POPULAR = "popular"
        private const val ORDER_BY_LATEST = "latest"
    }
}