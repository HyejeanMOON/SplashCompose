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

package com.hyejeanmoon.splashcompose.screen.userdetail

import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.UserDetail
import com.hyejeanmoon.splashcompose.entity.UsersPhotos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserDetailApiService {

    /**
     * get an user's details from server by UserName
     */
    @GET("/users/{userName}?utm_source=Splash_Pictures&utm_medium=referral")
    fun getUserDetails(
        @Path("userName") userName: String
    ): Call<UserDetail>

    /**
     * get a list of an user's photos by UserName
     */
    @GET("/users/{userName}/photos?utm_source=Splash_Pictures&utm_medium=referral")
    fun getPhotosByUserName(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<UsersPhotos>>

    /**
     * get a list of an user's liked photos by UserName
     */
    @GET("/users/{userName}/likes?utm_source=Splash_Pictures&utm_medium=referral")
    fun getLikedPhotosByUserName(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<UsersPhotos>>

    /**
     * get a list of an user's collections by UserName
     */
    @GET("/users/{userName}/collections?utm_source=Splash_Pictures&utm_medium=referral")
    fun getCollectionsByUserName(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<Collections>>
}
