package com.mashup.lastgarden.data.remote

import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.network.response.onErrorReturnDataNull
import com.mashup.lastgarden.network.services.PerfumeDetailService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerfumeDetailRemoteDataSource @Inject constructor(private val service: PerfumeDetailService) {

    suspend fun fetchPerfumeDetail(id: Int): Perfume? =
        service.getPerfumeDetail(id).onErrorReturnDataNull()

    suspend fun getStoryByPerfume(
        id: Int,
        cursor: Int? = null
    ): List<Story> = service.getStoryByPerfume(id, cursor)
        .onErrorReturnDataNull() ?: emptyList()
}