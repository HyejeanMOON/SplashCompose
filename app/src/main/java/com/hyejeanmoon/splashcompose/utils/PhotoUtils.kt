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

package com.hyejeanmoon.splashcompose.utils

import android.content.Context
import com.hyejeanmoon.splashcompose.EnvParameters
import com.hyejeanmoon.splashcompose.Resolution
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.entity.UsersPhotos
import com.hyejeanmoon.splashcompose.hilt.SystemComponentsModule

object PhotoUtils {

    fun getPhotoResolutionFromPref(context: Context):String{
        val pref = SystemComponentsModule().provideSharedPreferences(context)
        return pref.getString(EnvParameters.KEY_DISPLAY_RESOLUTION)
    }

    fun getPhotoUrlByResolution(resolution: String, photo: Photo?): String {
        return when (resolution) {
            Resolution.THUMB.name -> {
                photo?.urls?.thumb.orEmpty()
            }
            Resolution.SMALL.name -> {
                photo?.urls?.small.orEmpty()
            }
            Resolution.REGULAR.name -> {
                photo?.urls?.regular.orEmpty()
            }
            Resolution.FULL.name -> {
                photo?.urls?.full.orEmpty()
            }
            Resolution.RAW.name -> {
                photo?.urls?.raw.orEmpty()
            }
            else -> {
                ""
            }
        }
    }

    fun getUserDetailPhotoUrlByResolution(
        resolution: String,
        photo: UsersPhotos?
    ): String {
        return when (resolution) {
            Resolution.THUMB.name -> {
                photo?.urls?.thumb.orEmpty()
            }
            Resolution.SMALL.name -> {
                photo?.urls?.small.orEmpty()
            }
            Resolution.REGULAR.name -> {
                photo?.urls?.regular.orEmpty()
            }
            Resolution.FULL.name -> {
                photo?.urls?.full.orEmpty()
            }
            Resolution.RAW.name -> {
                photo?.urls?.raw.orEmpty()
            }
            else -> {
                ""
            }
        }
    }

    fun getCoverPhotoOfCollectionUrlByResolution(
        resolution: String,
        collections: Collections
    ): String {
        return when (resolution) {
            Resolution.THUMB.name -> {
                collections.coverPhoto?.urls?.thumb.orEmpty()
            }
            Resolution.SMALL.name -> {
                collections.coverPhoto?.urls?.small.orEmpty()
            }
            Resolution.REGULAR.name -> {
                collections.coverPhoto?.urls?.regular.orEmpty()
            }
            Resolution.FULL.name -> {
                collections.coverPhoto?.urls?.full.orEmpty()
            }
            Resolution.RAW.name -> {
                collections.coverPhoto?.urls?.raw.orEmpty()
            }
            else -> {
                ""
            }
        }
    }
}