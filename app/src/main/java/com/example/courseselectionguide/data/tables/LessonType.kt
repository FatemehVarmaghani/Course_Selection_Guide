package com.example.courseselectionguide.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonType (
    @PrimaryKey(autoGenerate = true)
    val typeId: Int? = null,
    val typeName: String,
    val allowedPrUnitsSum: Int? = null,
    val allowedTeUnitsSum: Int? = null
)