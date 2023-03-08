package com.hyejeanmoon.splashcompose.hilt

import android.content.SharedPreferences
import com.hyejeanmoon.splashcompose.screen.photos.PhotosDataSource
import com.hyejeanmoon.splashcompose.screen.photos.PhotosRepository
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailCollectionsDataSource
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailLikedPhotosDataSource
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailPhotosDataSource
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {

    @Provides
    fun providePhotosDataSource(
        photosRepository: PhotosRepository
    ): PhotosDataSource {
        return PhotosDataSource(photosRepository)
    }

    @Provides
    fun provideUserDetailCollectionsDataSource(
        userDetailRepository: UserDetailRepository,
        sharedPreferences: SharedPreferences
    ): UserDetailCollectionsDataSource {
        return UserDetailCollectionsDataSource(
            userDetailRepository,
            sharedPreferences
        )
    }

    @Provides
    fun provideUserDetailLikedPhotosDataSource(
        userDetailRepository: UserDetailRepository,
        sharedPreferences: SharedPreferences
    ): UserDetailLikedPhotosDataSource {
        return UserDetailLikedPhotosDataSource(
            userDetailRepository,
            sharedPreferences
        )
    }

    @Provides
    fun provideUserDetailPhotosDataSource(
        userDetailRepository: UserDetailRepository,
        sharedPreferences: SharedPreferences
    ):UserDetailPhotosDataSource {
        return UserDetailPhotosDataSource(
            userDetailRepository,
            sharedPreferences
        )
    }
}