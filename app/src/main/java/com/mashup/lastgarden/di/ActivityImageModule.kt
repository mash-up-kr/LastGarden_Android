package com.mashup.lastgarden.di

import android.app.Activity
import com.mashup.base.image.GlideApp
import com.mashup.base.image.GlideRequests
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
object ActivityImageModule {

    @Provides
    @Named("Activity")
    fun provideGlideRequests(activity: Activity): GlideRequests = GlideApp.with(activity)
}