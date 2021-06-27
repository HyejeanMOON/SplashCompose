package com.hyejeanmoon.splashcompose.screen.userdetail

import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo
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
    @GET("/users/{userName}")
    fun getUserDetails(
        @Path("userName") userName: String
    ): Call<UserDetail>

    /**
     * get a list of an user's photos by UserName
     */
    @GET("/users/{userName}/photos")
    fun getPhotosByUserName(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<UsersPhotos>>

    /**
     * get a list of an user's liked photos by UserName
     */
    @GET("/users/{userName}/likes")
    fun getLikedPhotosByUserName(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<UsersPhotos>>

    /**
     * get a list of an user's collections by UserName
     */
    @GET("/users/{userName}/collections")
    fun getCollectionsByUserName(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<Collections>>
}
