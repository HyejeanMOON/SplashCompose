package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CoverPhoto : Serializable {

    @Expose
    @SerializedName("id")
    val id: String? = null

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
    @SerializedName("likes")
    val likes: Int? = null

    @Expose
    @SerializedName("liked_by_user")
    val likedByUser: Boolean? = null

    @Expose
    @SerializedName("description")
    val description: String? = null

    @Expose
    @SerializedName("user")
    val user: User? = null

    @Expose
    @SerializedName("urls")
    val urls: Urls? = null

}