package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.PerfumeAndStories
import com.mashup.lastgarden.network.response.NetworkDataResponse
import retrofit2.http.GET

interface PerfumeService {

    @GET("home/today")
    suspend fun getTodayPerfume(): NetworkDataResponse<PerfumeAndStories>
}