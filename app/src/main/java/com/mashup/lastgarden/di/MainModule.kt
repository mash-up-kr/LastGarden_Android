package com.mashup.lastgarden.di

import androidx.fragment.app.Fragment
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.ui.main.HotStoryAdapter
import com.mashup.lastgarden.ui.main.MainFragment
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
    fun provideRankingAdapter(
        glideRequests: GlideRequests,
        fragment: Fragment
    ): PerfumeRankingAdapter =
        PerfumeRankingAdapter(glideRequests, fragment as? MainFragment)

    @Provides
    fun provideRecommendAdapter(
        glideRequests: GlideRequests,
        fragment: Fragment
    ): PerfumeRecommendAdapter =
        PerfumeRecommendAdapter(glideRequests, fragment as? MainFragment)
}