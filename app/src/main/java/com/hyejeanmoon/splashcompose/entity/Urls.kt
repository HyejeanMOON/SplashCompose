/*
 * Copyright (C) 2021 HyejeanMOON.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyejeanmoon.splashcompose.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Urls(

    @Expose
    @SerializedName("url")
    val url: String? = null,

    @Expose
    @SerializedName("raw")
    val raw: String? = null,

    @Expose
    @SerializedName("full")
    val full: String? = null,

    @Expose
    @SerializedName("regular")
    val regular: String? = null,

    @Expose
    @SerializedName("small")
    val small: String? = null,

    @Expose
    @SerializedName("thumb")
    val thumb: String? = null
) : Serializable