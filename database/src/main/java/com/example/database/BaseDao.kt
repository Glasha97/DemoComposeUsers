package com.example.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg args: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(list: List<T>)

    @Delete
    abstract suspend fun delete(arg: T)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(vararg arg: T)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun update(list: List<T>)

}