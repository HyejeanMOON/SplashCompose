package com.hyejeanmoon.splashcompose.screen.collections

import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsApiService {
    /**
     * get a list of photo collections curated.
     *
     * param:
     * page: optional, default 1;
     * per_page: optional, default 10;
     */
    @GET("/collections")
    fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<Collections>>

    /**
     * get a photo collection by id.
     *
     * param:
     * id: The collection’s ID. Required.
     * page: optional, default 1;
     * per_page: optional, default 10;
     */
    @GET("/collections/{id}/photos")
    fun getPhotosOfCollection(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<List<Photo>>
}