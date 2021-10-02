package com.mashup.lastgarden.di

import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.ui.main.HotStoryAdapter
import com.mashup.lastgarden.ui.main.PerfumeRankingAdapter
import com.mashup.lastgarden.ui.main.PerfumeRecommendAdapter
import com.mashup.lastgarden.ui.main.TodayPerfumeStoryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object MainModule {

    @Provides
    fun provideTodayPerfumeStoryAdapter(glideRequests: GlideRequests): TodayPerfumeStoryAdapter =
        TodayPerfumeStoryAdapter(glideRequests)

    @Provides
    fun provideHotStoryAdapter(glideRequests: GlideRequests): HotStoryAdapter =
        HotStoryAdapter(glideRequests)

    @Provides
    fun provideRankingAdapter(glideRequests: GlideRequests): PerfumeRankingAdapter =
        PerfumeRankingAdapter(glideRequests)

    @Provides
    fun provideRecommendAdapter(glideRequests: GlideRequests): PerfumeRecommendAdapter =
        PerfumeRecommendAdapter(glideRequests)
}