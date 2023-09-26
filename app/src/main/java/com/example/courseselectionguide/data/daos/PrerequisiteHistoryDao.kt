package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.courseselectionguide.data.tables.PrerequisiteHistory
import com.example.courseselectionguide.data.tables.Prerequisites

@Dao
interface PrerequisiteHistoryDao : BaseDao<Prerequisites> {
    @Query("insert into PrerequisiteHistory(mainLessonId, prerequisiteLessonId) values (:mainId, :preId)")
    fun insertPreHistoryRel(mainId: Int, preId: Int)

    @Query("select * from PrerequisiteHistory where prerequisiteLessonId = :id")
    fun getPreHistoryByPreId(id: Int): List<PrerequisiteHistory>

    @Query("delete from PrerequisiteHistory where prerequisiteLessonId = :id")
    fun deleteByPreLesson(id: Int)
}