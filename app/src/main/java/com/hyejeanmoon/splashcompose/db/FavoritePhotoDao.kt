package com.hyejeanmoon.splashcompose.db

import androidx.room.*

@Dao
interface FavoritePhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePhoto(favoritePhoto: FavoritePhoto)

    @Delete
    suspend fun deleteFavoritePhoto(favoritePhoto: FavoritePhoto)

    @Query("SELECT * FROM liked_photo WHERE id == :id")
    suspend fun isFavoritePhoto(id: String): List<FavoritePhoto>

    @Query("SELECT * FROM liked_photo")
    suspend fun getAllFavoritePhoto(): List<FavoritePhoto>
}