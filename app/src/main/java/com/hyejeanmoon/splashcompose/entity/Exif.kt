package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Exif(
    @Expose
    @SerializedName("make")
    val make: String? = null,

    @Expose
    @SerializedName("model")
    val model: String? = null,

    @Expose
    @SerializedName("exposure_time")
    val exposureTime: String? = null,

    @Expose
    @SerializedName("aperture")
    val aperture: String? = null,

    @Expose
    @SerializedName("focal_length")
    val focalLength: String? = null,

    @Expose
    @SerializedName("iso")
    val iso: Int? = null
) : Serializable