package com.hyejeanmoon.splashcompose.hilt

import android.content.SharedPreferences
import com.hyejeanmoon.splashcompose.screen.photos.PhotosDataSource
import com.hyejeanmoon.splashcompose.screen.photos.PhotosRepositoryImpl
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailCollectionsDataSource
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailLikedPhotosDataSource
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailPhotosDataSource
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {

    @Provides
    fun providePhotosDataSource(
        photosRepository: PhotosRepositoryImpl
    ): PhotosDataSource {
        return PhotosDataSource(photosRepository)
    }

    @Provides
    fun provideUserDetailCollectionsDataSource(
        userDetailRepository: UserDetailRepositoryImpl,
        sharedPreferences: SharedPreferences
    ): UserDetailCollectionsDataSource {
        return UserDetailCollectionsDataSource(
            userDetailRepository,
            sharedPreferences
        )
    }

    @Provides
    fun provideUserDetailLikedPhotosDataSource(
        userDetailRepository: UserDetailRepositoryImpl,
        sharedPreferences: SharedPreferences
    ): UserDetailLikedPhotosDataSource {
        return UserDetailLikedPhotosDataSource(
            userDetailRepository,
            sharedPreferences
        )
    }

    @Provides
    fun provideUserDetailPhotosDataSource(
        userDetailRepository: UserDetailRepositoryImpl,
        sharedPreferences: SharedPreferences
    ):UserDetailPhotosDataSource {
        return UserDetailPhotosDataSource(
            userDetailRepository,
            sharedPreferences
        )
    }
}