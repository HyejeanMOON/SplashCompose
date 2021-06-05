package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Values : Serializable {
    @Expose
    @SerializedName("date")
    val date: String? = null

    @Expose
    @SerializedName("value")
    val value: Int? = null
}