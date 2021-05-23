package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileImage {

    @Expose
    @SerializedName("small")
    val small: String? = null

    @Expose
    @SerializedName("medium")
    val medium: String? = null

    @Expose
    @SerializedName("large")
    val large: String? = null
}