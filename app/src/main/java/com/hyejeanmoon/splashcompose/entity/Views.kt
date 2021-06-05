package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Views : Serializable {

    @Expose
    @SerializedName("total")
    val total: Int? = null

    @Expose
    @SerializedName("historical")
    val historical: Historical? = null
}