package com.hyejeanmoon.splashcompose.hilt

import android.content.SharedPreferences
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsApiService
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsRepository
import com.hyejeanmoon.splashcompose.screen.photos.PhotosApiService
import com.hyejeanmoon.splashcompose.screen.photos.PhotosRepository
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailApiService
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun provideCollectionsRepository(
        apiService: CollectionsApiService
    ): CollectionsRepository {
        return CollectionsRepository(apiService)
    }

    @Provides
    fun providePhotosRepository(
        apiService: PhotosApiService,
        sharedPreferences: SharedPreferences
    ): PhotosRepository {
        return PhotosRepository(
            apiService,
            sharedPreferences
        )
    }

    @Provides
    fun provideUserDetailRepository(
        apiService: UserDetailApiService
    ): UserDetailRepository {
        return UserDetailRepository(apiService)
    }
}