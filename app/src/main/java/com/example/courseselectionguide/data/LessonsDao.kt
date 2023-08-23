package com.example.courseselectionguide.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    fun insert(obj: T)
    @Update
    fun update(obj: T)
    @Delete
    fun delete(obj: T)
}

@Dao
interface RemainedLessonsDao: BaseDao<Lessons> {
    @Query("select * from RemainedLessons")
    fun returnLessons()

    @Query("select * from RemainedLessons inner join Lessons on RemainedLessons.lessonId = Lessons.lessonId where lessonName like '%'||:searchText||'%'")
    fun searchLesson(searchText: String)

    @Query("select * from RemainedLessons inner join Lessons on RemainedLessons.lessonId = Lessons.lessonId where lessonTypeId = :typeId and unitType = :unitType")
    fun filterLessons(typeId: Int, unitType: Boolean)
}