package com.mashup.lastgarden.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mashup.lastgarden.data.vo.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUser(userId: String): User
}