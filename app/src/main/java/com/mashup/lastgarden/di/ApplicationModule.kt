package com.mashup.lastgarden.di

import com.mashup.lastgarden.data.PerfumeSharedPreferences
import com.mashup.lastgarden.data.PerfumeSharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Provides
    fun providePerfumeSharedPreferences(): PerfumeSharedPreferences = PerfumeSharedPreferencesImpl
}