package com.hyejeanmoon.splashcompose.api

import com.hyejeanmoon.splashcompose.screen.collections.CollectionsApiService
import com.hyejeanmoon.splashcompose.screen.photos.PhotosApiService
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

}