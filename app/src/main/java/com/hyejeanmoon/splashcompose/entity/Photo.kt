package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Photo {
    @Expose
    @SerializedName("id")
    val id: String? = null

    @Expose
    @SerializedName("created_at")
    val createdAt: String? = null

    @Expose
    @SerializedName("updated_at")
    val updatedAt: String? = null

    @Expose
    @SerializedName("width")
    val width: Int? = null

    @Expose
    @SerializedName("height")
    val height: Int? = null

    @Expose
    @SerializedName("color")
    val color: String? = null

    @Expose
    @SerializedName("downloads")
    val downloads: Int? = null

    @Expose
    @SerializedName("likes")
    val likes: Int? = null

    @Expose
    @SerializedName("liked_by_user")
    val likedByUser: Boolean? = null

    @Expose
    @SerializedName("description")
    val description: String? = null

    @Expose
    @SerializedName("exif")
    val exif: Exif? = null

    @Expose
    @SerializedName("links")
    val links: Links? = null

    @Expose
    @SerializedName("location")
    val location: Location? = null

    @Expose
    @SerializedName("tags")
    val tags: List<Tags>? = null

    @Expose
    @SerializedName("urls")
    val urls: Urls? = null

    @Expose
    @SerializedName("user")
    val user: User? = null

    @Expose
    @SerializedName("current_user_collections")
    val currentUserCollections: List<CurrentUserCollections>? = null
}