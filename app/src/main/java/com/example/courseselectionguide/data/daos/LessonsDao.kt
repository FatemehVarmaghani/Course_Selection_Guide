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

    @Query("select * from Lessons where lessonState = 1 or lessonState = 3")
    fun getRemainedLessons(): List<Lessons>

    @Query("select * from Lessons where lessonState = 2")
    fun getPassedLessons(): List<Lessons>

    @Query("select * from Lessons where lessonState = 3")
    fun getFailedLessons(): List<Lessons>

    @Query("select * from Lessons where (lessonState = 1 or lessonState = 3) and recommendedSemester = :currentSemester")
    fun getRecommendedLessons(currentSemester: Int): List<Lessons>

    @Query("select * from Lessons where (lessonState = 1 or lessonState = 3) and lessonName like '%' || :searching || '%'")
    fun searchOnRemained(searching: String): List<Lessons>

    @Query("select * from Lessons where lessonState = 2 and lessonName like '%' || :searching || '%'")
    fun searchOnPassed(searching: String): List<Lessons>

    @Query("select * from Lessons where lessonState = 3 and lessonName like '%' || :searching || '%'")
    fun searchOnFailed(searching: String): List<Lessons>

    @Query("select * from Lessons where (lessonState = 1 or lessonState = 3) and lessonTypeId = :typeId and isTheoretical = :isTheo")
    fun filterRemained(typeId: Int, isTheo: Boolean): List<Lessons>

    @Query("select * from Lessons where lessonState = 2 and lessonTypeId = :typeId and isTheoretical = :isTheo")
    fun filterPassed(typeId: Int, isTheo: Boolean): List<Lessons>

    @Query("select * from Lessons where lessonState = 3 and lessonTypeId = :typeId and isTheoretical = :isTheo")
    fun filterFailed(typeId: Int, isTheo: Boolean): List<Lessons>
}