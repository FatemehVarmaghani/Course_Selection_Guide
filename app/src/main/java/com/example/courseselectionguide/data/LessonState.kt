package com.example.courseselectionguide.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonState(
    @PrimaryKey(autoGenerate = true)
    val lessonStateId: Int? = null,
    val lessonStateName: String
)
