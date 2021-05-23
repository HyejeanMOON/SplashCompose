package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location {

    @Expose
    @SerializedName("city")
    val city: String? = null

    @Expose
    @SerializedName("country")
    val country: String? = null

    @Expose
    @SerializedName("position")
    val position: Position? = null

}