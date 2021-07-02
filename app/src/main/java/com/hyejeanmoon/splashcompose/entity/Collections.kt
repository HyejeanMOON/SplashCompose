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