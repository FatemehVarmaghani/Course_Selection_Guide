package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.courseselectionguide.data.tables.Prerequisites

@Dao
interface PrerequisitesDao : BaseDao<Prerequisites> {
    //get prerequisite relation
    @Query("select * from Prerequisites where mainLessonId = :id")
    fun getPrerequisitesByMainLesson(id: Int): List<Prerequisites>
    @Query("select * from Prerequisites where prerequisiteLessonId = :id")
    fun getPrerequisitesByPreLesson(id: Int): List<Prerequisites>

    //delete
    @Query("delete from Prerequisites where prerequisiteLessonId = :id")
    fun deleteByPreLesson(id: Int)

    //insert
    @Query("insert into Prerequisites(mainLessonId, prerequisiteLessonId) values (:mainId, :preId)")
    fun insertPreRel(mainId: Int, preId: Int)
}