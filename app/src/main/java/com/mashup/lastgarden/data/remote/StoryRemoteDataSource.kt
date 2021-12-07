package com.mashup.lastgarden.data.remote

import com.mashup.base.extensions.requestBodyOf
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.LikeResponse
import com.mashup.lastgarden.network.response.onErrorReturnData
import com.mashup.lastgarden.network.response.onErrorReturnDataNull
import com.mashup.lastgarden.network.services.StoryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRemoteDataSource @Inject constructor(private val service: StoryService) {

    suspend fun fetchHotStory(): List<Story> =
        service.getHotStories().onErrorReturnData(emptyList())

    suspend fun getPerfumeStoryList(storyId: Int, cursor: Int? = null): List<Story> =
        service.getPerfumeStoryList(storyId, cursor, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzZWVoeWFuZ19zZXJ2ZXIiLCJpZCI6MTMsImVtYWlsIjoia2hqdnZ2N0BuYXZlci5jb20iLCJvQXV0aFR5cGUiOiJLQUtBTyJ9.-AMVqSwXgbNPnl_97Wzys6TYzXCGsMuvENOECf5_0fw").onErrorReturnData(emptyList())

    suspend fun getPerfumeStory(storyId: Int): Story? =
        service.getPerfumeStory(storyId,"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzZWVoeWFuZ19zZXJ2ZXIiLCJpZCI6MTMsImVtYWlsIjoia2hqdnZ2N0BuYXZlci5jb20iLCJvQXV0aFR5cGUiOiJLQUtBTyJ9.-AMVqSwXgbNPnl_97Wzys6TYzXCGsMuvENOECf5_0fw").onErrorReturnDataNull()

    suspend fun getStoryLike(storyId: Int): LikeResponse? =
        service.getStoryLike(storyId,"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzZWVoeWFuZ19zZXJ2ZXIiLCJpZCI6MTMsImVtYWlsIjoia2hqdnZ2N0BuYXZlci5jb20iLCJvQXV0aFR5cGUiOiJLQUtBTyJ9.-AMVqSwXgbNPnl_97Wzys6TYzXCGsMuvENOECf5_0fw").onErrorReturnDataNull()

    suspend fun uploadStory(perfumeId: Int?, imageId: Int, tags: List<String>) =
        service.uploadStory(
            requestBodyOf {
                "perfumeId" to perfumeId
                "imageId" to imageId
                "tags" to tags
            }
        ).onErrorReturnDataNull()

    suspend fun getStoryCount(perfumeId: Int): Int? =
        service.getStoryCount(perfumeId).onErrorReturnDataNull()
}