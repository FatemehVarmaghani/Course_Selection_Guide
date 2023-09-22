package com.example.courseselectionguide.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonState(
    @PrimaryKey(autoGenerate = true)
    val lessonStateId: Int? = null,
    val lessonStateName: String
)

// remained lessons: 1
// passed lessons: 2
// failed lessons: 3
// selected lessons: 4
// recommended lessons: 5