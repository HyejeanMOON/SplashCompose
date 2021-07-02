package com.hyejeanmoon.splashcompose.db

import androidx.room.*

@Dao
interface FavoritePhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePhoto(likedPhoto: LikedPhoto)

    @Delete
    suspend fun deleteFavoritePhoto(likedPhoto: LikedPhoto)

    @Query("SELECT * FROM liked_photo WHERE id == :id")
    suspend fun isFavoritePhoto(id: String):List<LikedPhoto>
}