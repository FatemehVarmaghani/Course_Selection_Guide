package com.example.courseselectionguide.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonOrientation (
    @PrimaryKey(autoGenerate = true)
    val orientationId: Int? = null,
    val orientationName: String,
    val allowedUnitsSum: Int? = null
)