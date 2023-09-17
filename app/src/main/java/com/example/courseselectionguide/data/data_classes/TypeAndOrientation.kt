package com.example.courseselectionguide.data.data_classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonType(
    @PrimaryKey(autoGenerate = true)
    val typeId: Int,
    val typeName: String,
    val allowedPrUnitsSum: Int,
    val allowedTeUnitsSum: Int
)

@Entity
data class LessonOrientation(
    @PrimaryKey(autoGenerate = true)
    val orientationId: Int,
    val orientationName: String,
    val allowedPUnitsSum: Int
)