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

package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(

    @Expose
    @SerializedName("id")
    val id: String? = null,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @Expose
    @SerializedName("username")
    val userName: String? = null,

    @Expose
    @SerializedName("name")
    val name: String? = null,

    @Expose
    @SerializedName("portfolio_url")
    val portfolioUrl: String? = null,

    @Expose
    @SerializedName("bio")
    val bio: String? = null,

    @Expose
    @SerializedName("location")
    val location: String? = null,

    @Expose
    @SerializedName("total_likes")
    val totalLikes: Int? = null,

    @Expose
    @SerializedName("total_photos")
    val totalPhotos: Int? = null,

    @Expose
    @SerializedName("total_collections")
    val totalCollections: Int? = null,

    @Expose
    @SerializedName("links")
    val links: Links?,

    @Expose
    @SerializedName("profile_image")
    val profileImage: ProfileImage?,

    @Expose
    @SerializedName("instagram_username")
    val instagramUserName: String? = null,

    @Expose
    @SerializedName("twitter_username")
    val twitterUserName: String? = null
) : Serializable