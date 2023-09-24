package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.data.tables.SelectedLessons

@Dao
interface SelectedLessonsDao : BaseDao<SelectedLessons> {
    @Query("select lessonId, lessonName, lessonTypeId, unitNumber, isTheoretical, lessonOrientationId, neededPassedUnitsSum, recommendedSemester, lessonState, isFixed " +
            "from SelectedLessons inner join Lessons on SelectedLessons.selectedLessonId = Lessons.lessonId")
    fun getAllSelectedLessons(): List<Lessons>
}