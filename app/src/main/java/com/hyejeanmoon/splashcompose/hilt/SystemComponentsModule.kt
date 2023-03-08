package com.hyejeanmoon.splashcompose.hilt

import android.content.Context
import android.content.SharedPreferences
import com.hyejeanmoon.splashcompose.EnvParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class SystemComponentsModule {

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            EnvParameters.SHARED_PREFERENCE_DATA_NAME,
            Context.MODE_PRIVATE
        )
    }
}