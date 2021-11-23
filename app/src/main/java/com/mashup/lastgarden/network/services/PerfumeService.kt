package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.PerfumeAndStories
import com.mashup.lastgarden.network.response.NetworkDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PerfumeService {

    @GET("home/today")
    suspend fun getTodayPerfume(): NetworkDataResponse<PerfumeAndStories>

    @GET("home/weekly-ranking")
    suspend fun getWeeklyRanking(): NetworkDataResponse<List<Perfume>>

    @GET("home/steady-perfume")
    suspend fun getSteadyPerfumes(
        @Query("idCursor") idCursor: Int? = null,
        @Query("likeCursor") likeCursor: Int? = null
    ): NetworkDataResponse<List<Perfume>>
}