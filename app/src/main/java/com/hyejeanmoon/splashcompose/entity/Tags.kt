package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tags(
    @Expose
    @SerializedName("title")
    val title: String? = null
) : Serializable