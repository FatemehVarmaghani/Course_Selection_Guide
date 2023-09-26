package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.courseselectionguide.data.tables.Corequisites

@Dao
interface CorequisitesDao : BaseDao<Corequisites> {
    //get corequisite relations
    @Query("select * from Corequisites where mainLessonId = :id")
    fun getCorequisites(id: Int): List<Corequisites>

    //insert
    @Query("insert into Corequisites(mainLessonId, corequisiteLessonId) values (:mainId, :coId)")
    fun insertCorRel(mainId: Int, coId: Int)
}