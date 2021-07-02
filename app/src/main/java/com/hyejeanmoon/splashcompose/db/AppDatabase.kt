package com.hyejeanmoon.splashcompose.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [LikedPhoto::class])
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "user_database.db"

        private var INSTANCE: AppDatabase? = null
        private var lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "SplashCompose.db")
                            .build()

                    return INSTANCE!!
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }

    abstract fun getUserDao(): FavoritePhotoDao
}
