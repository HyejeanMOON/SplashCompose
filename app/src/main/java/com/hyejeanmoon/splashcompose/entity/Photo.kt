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

data class Photo(
    @Expose
    @SerializedName("id")
    val id: String? = null,

    @Expose
    @SerializedName("created_at")
    val createdAt: String? = null,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @Expose
    @SerializedName("width")
    val width: Int? = null,

    @Expose
    @SerializedName("height")
    val height: Int? = null,

    @Expose
    @SerializedName("color")
    val color: String? = null,

    @Expose
    @SerializedName("downloads")
    val downloads: Int? = null,

    @Expose
    @SerializedName("likes")
    val likes: Int? = null,

    @Expose
    @SerializedName("liked_by_user")
    val likedByUser: Boolean? = null,

    @Expose
    @SerializedName("description")
    val description: String? = null,

    @Expose
    @SerializedName("exif")
    val exif: Exif?,

    @Expose
    @SerializedName("links")
    val links: Links?,

    @Expose
    @SerializedName("location")
    val location: Location?,

    @Expose
    @SerializedName("tags")
    val tags: List<Tags>?,

    @Expose
    @SerializedName("urls")
    val urls: Urls?,

    @Expose
    @SerializedName("user")
    val user: User?,

    @Expose
    @SerializedName("current_user_collections")
    val currentUserCollections: List<CurrentUserCollections>?
) : Serializable