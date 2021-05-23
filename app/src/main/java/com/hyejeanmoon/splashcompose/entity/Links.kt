package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Links {
    @Expose
    @SerializedName("self")
    val self: String? = null

    @Expose
    @SerializedName("html")
    val html: String? = null

    @Expose
    @SerializedName("download")
    val download: String? = null

    @Expose
    @SerializedName("download_location")
    val downloadLocation: String? = null

    @Expose
    @SerializedName("photos")
    val photos: String? = null

    @Expose
    @SerializedName("likes")
    val likes: String? = null

    @Expose
    @SerializedName("portfolio")
    val portfolio: String? = null

}