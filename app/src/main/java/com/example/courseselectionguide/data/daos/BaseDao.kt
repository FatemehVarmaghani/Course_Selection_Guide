package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(vararg obj: T)

    @Update
    fun update(vararg obj: T)

    @Delete
    fun delete(vararg obj: T)

    //to insert the whole table
    @Insert
    fun insertAll(list: List<T>)
}