package com.mashup.lastgarden.data.remote

import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.PerfumeAndStories
import com.mashup.lastgarden.network.response.onErrorReturnDataNull
import com.mashup.lastgarden.network.services.PerfumeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerfumeRemoteDataSource @Inject constructor(private val service: PerfumeService) {

    suspend fun getTodayPerfume(): PerfumeAndStories? =
        service.getTodayPerfume().onErrorReturnDataNull()

    suspend fun getWeeklyRanking(): List<Perfume> =
        service.getWeeklyRanking().onErrorReturnDataNull()?.perfumes ?: emptyList()

    suspend fun getSteadyPerfumes(
        idCursor: String? = null,
        likeCursor: String? = null
    ): List<Perfume> = service.getSteadyPerfumes(idCursor, likeCursor)
        .onErrorReturnDataNull() ?: emptyList()
}