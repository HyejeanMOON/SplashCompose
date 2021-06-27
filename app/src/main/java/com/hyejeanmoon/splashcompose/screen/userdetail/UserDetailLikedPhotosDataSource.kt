package com.hyejeanmoon.splashcompose.screen.userdetail

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hyejeanmoon.splashcompose.entity.UsersPhotos

class UserDetailLikedPhotosDataSource(
    private val userDetailRepository: UserDetailRepository,
    private val userName: String
) : PagingSource<Int, UsersPhotos>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UsersPhotos> {
        val position = params.key ?: START_INDEX

        val photos = userDetailRepository.getUsersLikedPhotos(
            userName,
            page = position,
            perPage = params.loadSize
        )

        return LoadResult.Page(
            photos,
            if (position <= START_INDEX) null else position - 1,
            if (photos.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, UsersPhotos>): Int? {
        return START_INDEX
    }

    companion object {
        private const val START_INDEX = 0
    }
}