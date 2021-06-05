package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Tags : Serializable {
    @Expose
    @SerializedName("title")
    val title: String? = null
}