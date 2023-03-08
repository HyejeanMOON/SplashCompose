/*
 * Copyright (C) 2023 HyejeanMOON.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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