package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Historical(

    @Expose
    @SerializedName("change")
    val change: Int? = null,

    @Expose
    @SerializedName("average")
    val average: Int? = null,

    @Expose
    @SerializedName("resolution")
    val resolution: String? = null,

    @Expose
    @SerializedName("quantity")
    val quantity: Int? = null,

    @Expose
    @SerializedName("values")
    val values: List<Values>?
) : Serializable