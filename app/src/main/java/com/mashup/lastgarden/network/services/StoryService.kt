package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.NetworkDataResponse
import retrofit2.http.GET

interface StoryService {

    @GET("home/hot-story")
    suspend fun getHotStories(): NetworkDataResponse<List<Story>>
}