package com.mashup.lastgarden.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.mashup.lastgarden.data.vo.User

@Dao
abstract class UserDao : BaseDao<User>() {

    @Query("SELECT * FROM users WHERE id = :userId")
    abstract fun getUser(userId: String): User
}