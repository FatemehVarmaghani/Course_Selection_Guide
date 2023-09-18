package com.example.courseselectionguide.data.data_classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonType(
    @PrimaryKey(autoGenerate = true)
    val typeId: Int? = null,
    val typeName: String,
    val allowedPrUnitsSum: Int? = null,
    val allowedTeUnitsSum: Int? = null
)

@Entity
data class LessonOrientation(
    @PrimaryKey(autoGenerate = true)
    val orientationId: Int? = null,
    val orientationName: String,
    val allowedPUnitsSum: Int? = null
)