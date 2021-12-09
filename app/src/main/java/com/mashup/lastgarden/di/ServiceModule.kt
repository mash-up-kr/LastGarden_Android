package com.mashup.lastgarden.di

import com.mashup.base.network.PerfumeRetrofit
import com.mashup.lastgarden.network.services.PerfumeDetailService
import com.mashup.lastgarden.network.services.PerfumeService
import com.mashup.lastgarden.network.services.StoryService
import com.mashup.lastgarden.network.services.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideUserService(okHttpClient: OkHttpClient): UserService =
        PerfumeRetrofit.create(okHttpClient)

    @Provides
    fun providePerfumeService(okHttpClient: OkHttpClient): PerfumeService =
        PerfumeRetrofit.create(okHttpClient)

    @Provides
    fun provideStoryService(okHttpClient: OkHttpClient): StoryService =
        PerfumeRetrofit.create(okHttpClient)

    @Provides
    fun providePerfumeDetailService(okHttpClient: OkHttpClient): PerfumeDetailService =
        PerfumeRetrofit.create(okHttpClient)
}