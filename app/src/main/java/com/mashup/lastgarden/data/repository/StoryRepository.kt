package com.mashup.lastgarden.data.repository

import com.mashup.lastgarden.data.remote.StoryRemoteDataSource
import com.mashup.lastgarden.data.vo.Story
import javax.inject.Inject

class StoryRepository @Inject constructor(private val remote: StoryRemoteDataSource) {

    suspend fun fetchHotStory(): List<Story> = remote.fetchHotStory()
}