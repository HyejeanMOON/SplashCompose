package com.hyejeanmoon.splashcompose.screen.userdetail

import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.entity.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

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
        @Path("userName") userName: String
    ): Call<List<Photo>>

    /**
     * get a list of an user's liked photos by UserName
     */
    @GET("/users/{userName}/likes")
    fun getLikedPhotosByUserName(
        @Path("userName") userName: String
    ): Call<List<Photo>>

    /**
     * get a list of an user's collections by UserName
     */
    @GET("/users/{userName}/collections")
    fun getCollectionsByUserName(
        @Path("userName") userName: String
    ): Call<List<Collections>>
}
