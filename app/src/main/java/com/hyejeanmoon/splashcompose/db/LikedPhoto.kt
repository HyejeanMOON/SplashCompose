package com.hyejeanmoon.splashcompose.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_photo")
data class LikedPhoto(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "thumbUrl")
    val thumbUrl: String?

)