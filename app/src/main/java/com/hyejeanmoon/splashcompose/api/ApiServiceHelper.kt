/*
 * Copyright (C) 2021 HyejeanMOON.
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

package com.hyejeanmoon.splashcompose.api

import com.hyejeanmoon.splashcompose.screen.collections.CollectionsApiService
import com.hyejeanmoon.splashcompose.screen.photos.PhotosApiService
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object ApiServiceHelper {

    @Provides
    fun createPhotosApiService(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): PhotosApiService {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PhotosApiService::class.java)
    }

    @Provides
    fun createCollectionsApiService(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): CollectionsApiService {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CollectionsApiService::class.java)
    }

    @Provides
    fun createUserDetailApiService(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): UserDetailApiService {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(UserDetailApiService::class.java)
    }
}