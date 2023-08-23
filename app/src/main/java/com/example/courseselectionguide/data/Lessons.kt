package com.example.courseselectionguide.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lessons(
    @PrimaryKey(autoGenerate = true)
    val lessonId: Int? = null,
    val lessonName: String,
    val theoreticalUnitNumber: Int? = null,
    val practicalUnitNumber: Int? = null,
    val lessonTypeId: Int,
    // true is for theoretical, false for practical
    val unitType: Boolean,
    val lessonOrientationId: Int,
    val neededPassedUnitsSum: Int? = null,
    val listOfPrerequisites: PrerequisitesList? = null,
    val listOfCorequisites: CorequisitesList? = null,
    val recommendedSemester: Int
)
