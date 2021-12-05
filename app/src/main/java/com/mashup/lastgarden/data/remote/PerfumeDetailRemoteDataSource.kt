package com.mashup.lastgarden.data.remote

import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.PerfumeLike
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.onErrorReturnData
import com.mashup.lastgarden.network.response.onErrorReturnDataNull
import com.mashup.lastgarden.network.services.PerfumeDetailService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerfumeDetailRemoteDataSource @Inject constructor(private val service: PerfumeDetailService) {

    suspend fun fetchPerfumeDetail(token: String, id: Int): Perfume? =
        service.getPerfumeDetail(token, id).onErrorReturnDataNull()

    suspend fun getStoryByPerfume(
        id: Int,
        cursor: Int? = null
    ): List<Story> = service.getStoryByPerfume(id, cursor)
        .onErrorReturnData(emptyList())

    suspend fun likePerfume(token: String, id: Int): PerfumeLike? =
        service.likePerfume(token, id).onErrorReturnDataNull()
}