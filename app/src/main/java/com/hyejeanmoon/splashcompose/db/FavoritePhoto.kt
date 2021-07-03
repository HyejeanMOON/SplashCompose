package com.hyejeanmoon.splashcompose.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_photo")
data class FavoritePhoto(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String?

)