package com.hyejeanmoon.splashcompose.hilt

import android.content.Context
import androidx.room.Room
import com.hyejeanmoon.splashcompose.db.AppDatabase
import com.hyejeanmoon.splashcompose.db.FavoritePhotoDao
import com.hyejeanmoon.splashcompose.utils.EnvParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabaseInstance(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            EnvParameters.DB_NAME
        ).build()
    }

    @Provides
    fun provideUserDao(
        appDatabase: AppDatabase
    ):FavoritePhotoDao{
        return appDatabase.favoritePhotoDao()
    }
}