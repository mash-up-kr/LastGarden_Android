package com.mashup.lastgarden.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.mashup.lastgarden.data.db.PerfumeDatabase
import com.mashup.lastgarden.data.db.dao.PerfumeDao
import com.mashup.lastgarden.data.db.dao.StoryDao
import com.mashup.lastgarden.data.paging.PerfumeRecommendPagingSource
import com.mashup.lastgarden.data.remote.PerfumeRemoteDataSource
import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.PerfumeAndStories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerfumeRepository @Inject constructor(
    private val database: PerfumeDatabase,
    private val remote: PerfumeRemoteDataSource,
    private val storyDao: StoryDao,
    private val perfumeDao: PerfumeDao
) {

    suspend fun fetchTodayPerfume(): PerfumeAndStories? {
        val todayPerfumeAndStories = remote.getTodayPerfume()
        val perfume = todayPerfumeAndStories?.perfume ?: return null
        val stories = todayPerfumeAndStories.stories ?: emptyList()
        database.withTransaction {
            perfumeDao.insertOrUpdate(perfume)
            storyDao.insertOrUpdate(stories)
        }
        return todayPerfumeAndStories
    }

    suspend fun fetchWeeklyRanking(): List<Perfume> = remote.getWeeklyRanking()

    suspend fun fetchSteadyPerfumes(): List<Perfume> = remote.getSteadyPerfumes()

    fun getRecommendPerfumes(pageSize: Int): Flow<PagingData<Perfume>> =
        Pager(PagingConfig(pageSize)) {
            PerfumeRecommendPagingSource(remote)
        }.flow
}