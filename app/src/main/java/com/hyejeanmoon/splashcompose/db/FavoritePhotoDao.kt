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