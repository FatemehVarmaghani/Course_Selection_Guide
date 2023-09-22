package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.courseselectionguide.data.tables.Lessons

@Dao
interface LessonsDao: BaseDao<Lessons> {

    @Insert
    fun insertAllLessons(list: List<Lessons>)

    @Query("select * from Lessons")
    fun getAllLessons(): List<Lessons>

}