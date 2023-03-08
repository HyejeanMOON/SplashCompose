package com.hyejeanmoon.splashcompose.hilt

import com.hyejeanmoon.splashcompose.screen.collections.CollectionsRepository
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsRepositoryImpl
import com.hyejeanmoon.splashcompose.screen.photos.PhotosRepository
import com.hyejeanmoon.splashcompose.screen.photos.PhotosRepositoryImpl
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailApiService
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailRepository
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCollectionsRepository(
        collectionsRepositoryImpl: CollectionsRepositoryImpl
    ): CollectionsRepository

    @Binds
    abstract fun providePhotosRepository(
        photosRepositoryImpl: PhotosRepositoryImpl
    ): PhotosRepository

    @Binds
    abstract fun provideUserDetailRepository(
        userDetailRepositoryImpl: UserDetailRepositoryImpl
    ): UserDetailRepository
}