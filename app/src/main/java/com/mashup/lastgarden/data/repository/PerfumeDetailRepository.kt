package com.mashup.lastgarden.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mashup.lastgarden.data.paging.PerfumeDetailPagingSource
import com.mashup.lastgarden.data.remote.PerfumeDetailRemoteDataSource
import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.PerfumeLike
import com.mashup.lastgarden.data.vo.Story
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerfumeDetailRepository @Inject constructor(private val remote: PerfumeDetailRemoteDataSource) {

    suspend fun fetchPerfumeDetail(token: String, id: Int): Perfume? =
        remote.fetchPerfumeDetail(token, id)

    fun getStoryByPerfume(id: Int, pageSize: Int): Flow<PagingData<Story>> =
        Pager(PagingConfig(pageSize)) {
            PerfumeDetailPagingSource(id, remote)
        }.flow

    suspend fun likePerfume(token: String, id: Int): PerfumeLike? = remote.likePerfume(token, id)
}