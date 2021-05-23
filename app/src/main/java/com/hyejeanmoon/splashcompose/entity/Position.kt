package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Position {

    @Expose
    @SerializedName("latitude")
    val latitude: Double? = null

    @Expose
    @SerializedName("longitude")
    val longitude: Double? = null
}