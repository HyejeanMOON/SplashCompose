package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Position : Serializable {

    @Expose
    @SerializedName("latitude")
    val latitude: Double? = null

    @Expose
    @SerializedName("longitude")
    val longitude: Double? = null
}