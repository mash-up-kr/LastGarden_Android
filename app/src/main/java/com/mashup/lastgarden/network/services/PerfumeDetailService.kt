package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.PerfumeLike
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.NetworkDataResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PerfumeDetailService {

    @GET("perfume/{id}")
    suspend fun getPerfumeDetail(
        @Path("id") id: Int
    ): NetworkDataResponse<Perfume>

    @GET("perfume/{id}/story")
    suspend fun getStoryByPerfume(
        @Path("id") id: Int,
        @Query("cursor") cursor: Int? = null
    ): NetworkDataResponse<List<Story>>

    @POST("perfume/{id}/like")
    suspend fun likePerfume(
        @Path("id") id: Int
    ): NetworkDataResponse<PerfumeLike>

    @GET("perfume/{id}/storyCount")
    suspend fun getStoryCount(
        @Path("id") perfumeId: Int
    ): NetworkDataResponse<Int>
}