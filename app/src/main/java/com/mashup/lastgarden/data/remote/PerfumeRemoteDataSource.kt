package com.mashup.lastgarden.data.remote

import com.mashup.lastgarden.data.vo.Image
import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.PerfumeAndStories
import com.mashup.lastgarden.network.response.onErrorReturnData
import com.mashup.lastgarden.network.response.onErrorReturnDataNull
import com.mashup.lastgarden.network.services.PerfumeService
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerfumeRemoteDataSource @Inject constructor(private val service: PerfumeService) {

    suspend fun getTodayPerfume(): PerfumeAndStories? =
        service.getTodayPerfume().onErrorReturnDataNull()

    suspend fun getWeeklyRanking(): List<Perfume> =
        service.getWeeklyRanking().onErrorReturnData(emptyList())

    suspend fun getSteadyPerfumes(
        idCursor: Int? = null,
        likeCursor: Int? = null
    ): List<Perfume> = service.getSteadyPerfumes(idCursor, likeCursor)
        .onErrorReturnDataNull() ?: emptyList()

    suspend fun getPerfumesWithName(name: String, cursor: Int?): List<Perfume> =
        service.getPerfumesList(name = name, cursor = cursor).onErrorReturnData(emptyList())

    suspend fun uploadImage(image: MultipartBody.Part): Image? =
        service.uploadImage(image).onErrorReturnDataNull()
}