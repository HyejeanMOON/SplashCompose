package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Statistics : Serializable {

    @Expose
    @SerializedName("username")
    val userName: String? = null

    @Expose
    @SerializedName("downloads")
    val downloads: Downloads? = null

    @Expose
    @SerializedName("views")
    val views: Views? = null

    @Expose
    @SerializedName("likes")
    val likes: Likes? = null
}