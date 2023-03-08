package com.hyejeanmoon.splashcompose.hilt

import com.hyejeanmoon.splashcompose.api.SplashOkHttpClient
import com.hyejeanmoon.splashcompose.screen.collections.CollectionsApiService
import com.hyejeanmoon.splashcompose.screen.photos.PhotosApiService
import com.hyejeanmoon.splashcompose.screen.userdetail.UserDetailApiService
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class ApiComponentsModule {

    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return SplashOkHttpClient().splashOkHttpClient
    }

    @Provides
    fun provideCollectionsApiService(
        okHttpClient: OkHttpClient
    ): CollectionsApiService {
        return Retrofit
            .Builder()
            .baseUrl(EnvParameters.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CollectionsApiService::class.java)
    }

    @Provides
    fun providePhotosApiService(
        okHttpClient: OkHttpClient
    ): PhotosApiService {
        return Retrofit
            .Builder()
            .baseUrl(EnvParameters.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotosApiService::class.java)
    }

    @Provides
    fun provideUserDetailApiService(
        okHttpClient: OkHttpClient
    ): UserDetailApiService {
        return Retrofit
            .Builder()
            .baseUrl(EnvParameters.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserDetailApiService::class.java)
    }
}