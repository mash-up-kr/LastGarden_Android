package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Comment
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.LikeResponse
import com.mashup.lastgarden.network.response.NetworkDataResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StoryService {

    @GET("home/hot-story")
    suspend fun getHotStories(): NetworkDataResponse<List<Story>>

    @GET("perfume/{id}/story")
    suspend fun getPerfumeStoryList(
        @Path("id") storyId: Int,
        @Query("cursor") cursor: Int? = null
    ): NetworkDataResponse<List<Story>>

    @GET("story/{id}")
    suspend fun getPerfumeStory(
        @Path("id") storyId: Int
    ): NetworkDataResponse<Story>

    @POST("story/{id}/like")
    suspend fun getStoryLike(
        @Path("id") storyId: Int
    ): NetworkDataResponse<LikeResponse>

    @POST("story")
    suspend fun uploadStory(
        @Body body: RequestBody
    ): NetworkDataResponse<Story>

    @GET("story/{id}/comment")
    suspend fun getCommentList(
        @Path("id") storyId: Int,
        @Query("cursor") cursor: Int? = null
    ): NetworkDataResponse<List<Comment>>

    @POST("story/{id}/comment")
    suspend fun addComment(
        @Path("id") storyId: Int,
        @Body body: RequestBody
    ): NetworkDataResponse<String>
}