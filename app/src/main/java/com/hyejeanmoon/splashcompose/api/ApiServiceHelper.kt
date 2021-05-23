package com.hyejeanmoon.splashcompose.api

import com.hyejeanmoon.splashcompose.photo.PhotosApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceHelper {

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

}