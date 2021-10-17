package com.mashup.lastgarden.data.repository

import com.mashup.lastgarden.data.remote.StoryRemoteDataSource
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.LikeResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepository @Inject constructor(
    private val remote: StoryRemoteDataSource,
) {
    suspend fun fetchHotStory(): List<Story> = remote.fetchHotStory()

    suspend fun fetchPerfumeStoryList(storyId: Int): List<Story> =
        remote.getPerfumeStoryList(storyId)

    suspend fun fetchPerfumeStory(storyId: Int): Story? =
        remote.getPerfumeStory(storyId)

    suspend fun getStoryLike(storyId: Int): LikeResponse? =
        remote.getStoryLike(storyId)

    suspend fun uploadStory(perfumeId: Int?, imageId: Int, tags: List<String>) =
        remote.uploadStory(perfumeId, imageId, tags)
}