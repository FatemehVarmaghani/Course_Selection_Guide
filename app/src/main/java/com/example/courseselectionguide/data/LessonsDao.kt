package com.example.courseselectionguide.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    fun insertAll(vararg obj: T)
    @Update
    fun update(obj: T)
    @Delete
    fun delete(vararg obj: T)
}

@Dao
interface LessonsDao: BaseDao<Lessons>

@Dao
interface RemainedLessonsDao: BaseDao<RemainedLessons> {
    @Query("select * from RemainedLessons")
    fun returnLessons()

    @Query("select * from RemainedLessons inner join Lessons on RemainedLessons.lessonId = Lessons.lessonId where lessonName like '%'||:searchText||'%'")
    fun searchLesson(searchText: String)

    @Query("select * from RemainedLessons inner join Lessons on RemainedLessons.lessonId = Lessons.lessonId where lessonTypeId = :typeId and unitType = :unitType")
    fun filterLessons(typeId: Int, unitType: Boolean)
}

@Dao
interface SelectedLessonsDao: BaseDao<SelectedLessons> {
    @Query("select * from SelectedLessons")
    fun returnLessons()
}

@Dao
interface RecommendedLessonsDao: BaseDao<RecommendedLessons> {
    @Query("select * from RecommendedLessons")
    fun returnLessons()

    @Query("select * from RemainedLessons inner join Lessons on RemainedLessons.lessonId = Lessons.lessonId")
    fun changeRecommendedLessons(currentSemester: Int)
}

@Dao
interface PassedLessonsDao: BaseDao<PassedLessons> {
    @Query("select * from PassedLessons")
    fun returnLessons()
}

@Dao
interface FailedLessonsDao: BaseDao<FailedLessons> {
    @Query("select * from failedLessons")
    fun returnLessons()
}

@Dao
interface LessonTypeDao: BaseDao<LessonType> {
    @Query("select * from failedLessons")
    fun returnLessons()
}

@Dao
interface LessonOrientationDao: BaseDao<LessonOrientation> {
    @Query("select * from failedLessons")
    fun returnLessons()
}