package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.courseselectionguide.data.tables.Lessons

@Dao
interface LessonsDao: BaseDao<Lessons> {
    //insert data
    @Insert
    fun insertAllLessons(list: List<Lessons>)

    //get data
    @Query("select * from Lessons where lessonId = :id")
    fun getLesson(id: Int): Lessons
    @Query("select * from Lessons")
    fun getAllLessons(): List<Lessons>

    //get list
    @Query("select * from Lessons where lessonState = 3 or lessonState = 1")
    fun getRemainedLessons(): List<Lessons>
    @Query("select * from Lessons where lessonState = 2")
    fun getPassedLessons(): List<Lessons>
    @Query("select * from Lessons where lessonState = 3")
    fun getFailedLessons(): List<Lessons>
    @Query("select * from Lessons where lessonState = 4")
    fun getSelectedLessons(): List<Lessons>

    //get recommended
    @Query("select * from Lessons where (lessonState = 3 or lessonState = 1) and recommendedSemester = :semesterId")
    fun getRecommendedLessons(semesterId: Int): List<Lessons>

    //search on list
    @Query("select * from Lessons where (lessonState = 3 or lessonState = 1) and lessonName like '%' || :searching || '%'")
    fun searchOnRemained(searching: String): List<Lessons>
    @Query("select * from Lessons where lessonState = 2 and lessonName like '%' || :searching || '%'")
    fun searchOnPassed(searching: String): List<Lessons>
    @Query("select * from Lessons where lessonState = 3 and lessonName like '%' || :searching || '%'")
    fun searchOnFailed(searching: String): List<Lessons>

    //filter data
    @Query("select * from Lessons where (lessonState = 3 or lessonState = 1) and lessonTypeId = :typeId and isTheoretical = :isTheo")
    fun filterRemained(typeId: Int, isTheo: Boolean): List<Lessons>
    @Query("select * from Lessons where lessonState = 2 and lessonTypeId = :typeId and isTheoretical = :isTheo")
    fun filterPassed(typeId: Int, isTheo: Boolean): List<Lessons>
    @Query("select * from Lessons where lessonState = 3 and lessonTypeId = :typeId and isTheoretical = :isTheo")
    fun filterFailed(typeId: Int, isTheo: Boolean): List<Lessons>

    //change lesson state
    @Query("update Lessons set lessonState = 2 where lessonId = :id")
    fun changeToPassed(id: Int)
    @Query("update Lessons set lessonState = 4 where lessonId = :id")
    fun changeToSelected(id: Int)
    @Query("update Lessons set lessonState = 1 where lessonId = :id")
    fun changeToRemained(id: Int)
    @Query("update Lessons set lessonState = 3 where lessonId = :id")
    fun changeToFailed(id: Int)

    //get lesson values
    @Query("select lessonState from Lessons where lessonId = :id")
    fun getLessonState(id: Int): Int

    //get count
    @Query("select count(*) from Lessons where lessonTypeId = 1 and lessonState = 4")
    fun countSelectedIslamics(): Int
    @Query("select count(*) from Lessons where lessonOrientationId = :orientationId and lessonState = 2")
    fun countPassedLessonsByOrientation(orientationId: Int): Int
    @Query("select count(*) from Lessons where isTheoretical = 0 and (lessonState = 2 or lessonState = 4) and lessonTypeId = :typeId")
    fun countPassedOrSelectedPracticalLessonsByType(typeId: Int): Int
    @Query("select count(*) from Lessons where lessonState = 4 and isTheoretical = 1")
    fun countTheoreticalSelected(): Int
}