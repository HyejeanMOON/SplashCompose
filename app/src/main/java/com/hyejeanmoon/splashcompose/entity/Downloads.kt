package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Downloads {
    @Expose
    @SerializedName("total")
    val total: Int? = null

    @Expose
    @SerializedName("historical")
    val historical: Historical? = null
}