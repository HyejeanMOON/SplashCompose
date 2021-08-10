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

package com.hyejeanmoon.splashcompose.screen.photos

import com.hyejeanmoon.splashcompose.entity.DownloadPhoto
import com.hyejeanmoon.splashcompose.entity.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotosApiService {
    /**
     * get a photo from server by id.
     */
    @GET("/photos/{id}?utm_source=Splash_Pictures&utm_medium=referral")
    fun getPhoto(@Path("id") id: String): Call<Photo>

    /**
     * get a list of photos from server.
     */
    @GET("/photos?utm_source=Splash_Pictures&utm_medium=referral")
    fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<Photo>>

    /**
     * get a photo from server by random.
     *
     * param:
     * collections:Public collection ID(‘s) to filter selection. If multiple, comma-separated
     * featured: Limit selection to featured photos.
     * username: Limit selection to a single user.
     * query: Limit selection to photos matching a search term.
     * orientation: Filter search results by photo orientation. Valid values are landscape, portrait, and squarish.
     * count: The number of photos to return. (Default: 1; max: 30)
     */
    @GET("/photos/random?utm_source=Splash_Pictures&utm_medium=referral")
    fun getRandomPhotos(
        @Query("collections") collections: String,
        @Query("featured") featured: String,
        @Query("username") username: String,
        @Query("query") query: String,
        @Query("orientation") orientation: String,
        @Query("count") count: String
    ): Call<List<Photo>>

    /**
     * get a photo from server by random, without any parameters.
     */
    @GET("/photos/random?utm_source=Splash_Pictures&utm_medium=referral")
    fun getRandomPhoto(): Call<Photo>

    /**
     * download a photo by id.
     */
    @GET("/photos/{id}/download?utm_source=Splash_Pictures&utm_medium=referral")
    fun downloadPhoto(
        @Path("id") id: String
    ):Call<DownloadPhoto>
}