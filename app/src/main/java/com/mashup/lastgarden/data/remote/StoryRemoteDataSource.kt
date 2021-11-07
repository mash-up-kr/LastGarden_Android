package com.mashup.lastgarden.data.remote

import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.onErrorReturnData
import com.mashup.lastgarden.network.services.StoryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRemoteDataSource @Inject constructor(private val service: StoryService) {

    suspend fun fetchHotStory(): List<Story> =
        service.getHotStories().onErrorReturnData(emptyList())
}