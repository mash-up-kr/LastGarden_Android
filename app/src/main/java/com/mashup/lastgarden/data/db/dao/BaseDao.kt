package com.mashup.lastgarden.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update

@Dao
abstract class BaseDao<T : Any> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg objects: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(objects: List<T>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertOrIgnore(vararg objects: T): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertOrIgnore(interests: List<T>): List<Long>

    @Update
    abstract fun update(vararg objects: T)

    @Update
    abstract fun update(objects: List<T>): Int

    @Delete
    abstract fun delete(vararg objects: T)

    @Delete
    abstract fun delete(objects: List<T>)

    @Transaction
    open fun insertOrUpdate(vararg objects: T) {
        insertOrIgnore(*objects)
            .zip(objects)
            .mapNotNull { (result: Long, obj: T) -> obj.takeIf { result == -1L } }
            .also { update(it) }
    }

    @Transaction
    open fun insertOrUpdate(objects: List<T>) {
        insertOrIgnore(objects)
            .zip(objects)
            .mapNotNull { (result: Long, obj: T) -> obj.takeIf { result == -1L } }
            .also { update(it) }
    }

    @Transaction
    open fun insertAndRemove(
        newData: List<T>,
        getCurrentData: () -> List<T>,
        isSameData: (old: T, new: T) -> Boolean
    ) {
        val oldData = getCurrentData()
        val removedInNewData =
            oldData.filter { old -> newData.all { new -> !isSameData(old, new) } }
        delete(removedInNewData)
        insertOrUpdate(newData)
    }
}
