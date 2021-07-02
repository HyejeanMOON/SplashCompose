package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Urls(

    @Expose
    @SerializedName("url")
    val url: String? = null,

    @Expose
    @SerializedName("raw")
    val raw: String? = null,

    @Expose
    @SerializedName("full")
    val full: String? = null,

    @Expose
    @SerializedName("regular")
    val regular: String? = null,

    @Expose
    @SerializedName("small")
    val small: String? = null,

    @Expose
    @SerializedName("thumb")
    val thumb: String? = null
) : Serializable