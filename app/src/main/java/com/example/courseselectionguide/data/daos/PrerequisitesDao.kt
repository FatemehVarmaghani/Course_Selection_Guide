package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.courseselectionguide.data.tables.Prerequisites

@Dao
interface PrerequisitesDao : BaseDao<Prerequisites> {
    //get prerequisite relation
    @Query("select * from Prerequisites where mainLessonId = :id")
    fun getPrerequisites(id: Int): List<Prerequisites>
}