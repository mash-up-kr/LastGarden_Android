package com.mashup.lastgarden.data.repository

import androidx.room.withTransaction
import com.mashup.lastgarden.data.db.PerfumeDatabase
import com.mashup.lastgarden.data.db.dao.PerfumeDao
import com.mashup.lastgarden.data.db.dao.StoryDao
import com.mashup.lastgarden.data.remote.PerfumeRemoteDataSource
import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.PerfumeAndStories
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
}