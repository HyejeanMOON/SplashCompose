package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DownloadPhoto(
    @Expose
    @SerializedName("url")
    val url: String? = null
) : Serializable