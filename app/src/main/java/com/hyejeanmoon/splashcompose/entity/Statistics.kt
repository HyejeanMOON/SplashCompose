package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Statistics(

    @Expose
    @SerializedName("username")
    val userName: String? = null,

    @Expose
    @SerializedName("downloads")
    val downloads: Downloads?,

    @Expose
    @SerializedName("views")
    val views: Views?,

    @Expose
    @SerializedName("likes")
    val likes: Likes?
) : Serializable