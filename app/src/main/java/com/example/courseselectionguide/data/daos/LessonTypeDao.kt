package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import com.example.courseselectionguide.data.tables.LessonType

@Dao
interface LessonTypeDao : BaseDao<LessonType> {
}