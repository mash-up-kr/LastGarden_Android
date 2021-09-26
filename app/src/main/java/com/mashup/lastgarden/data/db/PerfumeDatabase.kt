package com.mashup.lastgarden.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mashup.lastgarden.data.db.dao.UserDao
import com.mashup.lastgarden.data.vo.User

@Database(entities = [User::class], version = 1)
abstract class PerfumeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: PerfumeDatabase? = null
        private const val DB_NAME = "perfume-database"

        @Synchronized
        fun getInstance(context: Context): PerfumeDatabase? {
            if (instance == null) {
                synchronized(PerfumeDatabase::class) {
                    instance ?: Room.databaseBuilder(
                        context.applicationContext,
                        PerfumeDatabase::class.java,
                        DB_NAME
                    ).build().also {
                        instance = it
                    }
                }
            }
            return instance
        }
    }
}