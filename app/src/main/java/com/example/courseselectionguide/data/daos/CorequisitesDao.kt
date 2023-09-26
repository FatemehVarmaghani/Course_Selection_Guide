package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.courseselectionguide.data.tables.Corequisites

@Dao
interface CorequisitesDao : BaseDao<Corequisites> {
    //get corequisite relations
    @Query("select * from Corequisites where mainLessonId = :id")
    fun getCorequisitesByMainLesson(id: Int): List<Corequisites>

    @Query("select * from Corequisites where corequisiteLessonId = :id")
    fun getCorequisitesByCoLesson(id: Int): List<Corequisites>

    //insert
    @Query("insert into Corequisites(mainLessonId, corequisiteLessonId) values (:mainId, :coId)")
    fun insertCorRel(mainId: Int, coId: Int)

    //delete
    @Query("delete from Corequisites where corequisiteLessonId = :id")
    fun deleteByCoLesson(id: Int)
}