package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CurrentUserCollections(
    @Expose
    @SerializedName("id")
    val id: Int? = null,

    @Expose
    @SerializedName("title")
    val title: String? = null,

    @Expose
    @SerializedName("published_at")
    val publishedAt: String? = null,

    @Expose
    @SerializedName("curated")
    val curated: Boolean? = null,

    @Expose
    @SerializedName("cover_photo")
    val coverPhoto: String? = null,

    @Expose
    @SerializedName("user")
    val user: String? = null
) : Serializable