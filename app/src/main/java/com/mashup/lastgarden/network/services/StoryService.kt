package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.NetworkDataResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface StoryService {

    @GET("home/hot-story")
    suspend fun getHotStories(): NetworkDataResponse<List<Story>>

    @GET("perfume/{id}/story")
    suspend fun getPerfumeStoryList(
        @Path("id") storyId: Int
    ): NetworkDataResponse<List<Story>>

    @GET("story/{id}")
    suspend fun getPerfumeStory(
        @Path("id") storyId: Int
    ): NetworkDataResponse<Story>

    @POST("story/{id}/like")
    suspend fun getStoryLike(
        @Header("Authorization") token: String,
        @Path("id") storyId: Int
    ): NetworkDataResponse<LikeResponse>
}

data class LikeResponse(
    val isLike: Boolean
)