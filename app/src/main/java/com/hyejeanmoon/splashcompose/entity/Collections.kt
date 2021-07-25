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

data class Collections(
    @Expose
    @SerializedName("id")
    val id: String? = null,

    @Expose
    @SerializedName("title")
    val title: String? = null,

    @Expose
    @SerializedName("description")
    val description: String? = null,

    @Expose
    @SerializedName("published_at")
    val publishedAt: String? = null,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @Expose
    @SerializedName("curated")
    val curated: String? = null,

    @Expose
    @SerializedName("total_photos")
    val totalPhotos: String? = null,

    @Expose
    @SerializedName("private")
    val pri: String? = null,

    @Expose
    @SerializedName("share_key")
    val shareKey: String? = null,

    @Expose
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto?,

    @Expose
    @SerializedName("user")
    val user: User?,

    @Expose
    @SerializedName("links")
    val links: Links?
) : Serializable