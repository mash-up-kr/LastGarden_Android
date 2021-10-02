package com.mashup.lastgarden.di

import android.content.Context
import com.mashup.lastgarden.data.db.PerfumeDatabase
import com.mashup.lastgarden.data.db.dao.PerfumeDao
import com.mashup.lastgarden.data.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun providePerfumeDatabase(@ApplicationContext context: Context): PerfumeDatabase =
        PerfumeDatabase.getInstance(context)

    @Provides
    fun provideUserDao(perfumeDatabase: PerfumeDatabase): UserDao = perfumeDatabase.userDao()

    @Provides
    fun providePerfumeDao(perfumeDatabase: PerfumeDatabase): PerfumeDao = perfumeDatabase.perfumeDao()
}