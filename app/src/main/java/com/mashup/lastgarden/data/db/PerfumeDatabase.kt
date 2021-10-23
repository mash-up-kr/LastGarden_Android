package com.mashup.lastgarden.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mashup.lastgarden.data.db.converters.PerfumeDatabaseConverters
import com.mashup.lastgarden.data.db.dao.PerfumeDao
import com.mashup.lastgarden.data.db.dao.StoryDao
import com.mashup.lastgarden.data.db.dao.UserDao
import com.mashup.lastgarden.data.vo.Note
import com.mashup.lastgarden.data.vo.Perfume
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.data.vo.Tag
import com.mashup.lastgarden.data.vo.User

@Database(
    entities = [
        User::class,
        Perfume::class,
        Story::class,
        Note::class,
        Tag::class
    ],
    version = 1
)
@TypeConverters(PerfumeDatabaseConverters::class)
abstract class PerfumeDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: PerfumeDatabase? = null
        private const val DB_NAME = "perfume-database"

        fun getInstance(context: Context): PerfumeDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): PerfumeDatabase =
            Room.databaseBuilder(context, PerfumeDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun userDao(): UserDao
    abstract fun perfumeDao(): PerfumeDao
    abstract fun storyDao(): StoryDao
}