package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Badge(
    @Expose
    @SerializedName("title")
    val title: String? = null,

    @Expose
    @SerializedName("primary")
    val primary: Boolean? = null,

    @Expose
    @SerializedName("slug")
    val slug: String? = null,

    @Expose
    @SerializedName("link")
    val link: String? = null,
) : Serializable