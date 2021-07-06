package com.hyejeanmoon.splashcompose.utils

import android.content.Context
import com.hyejeanmoon.splashcompose.Resolution
import com.hyejeanmoon.splashcompose.entity.Collections
import com.hyejeanmoon.splashcompose.entity.Photo
import com.hyejeanmoon.splashcompose.entity.UsersPhotos

object PhotoUtils {

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
        collections:Collections
    ):String{
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