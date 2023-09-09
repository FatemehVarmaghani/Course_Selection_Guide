package com.example.courseselectionguide.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lessons(
    @PrimaryKey(autoGenerate = true)
    val lessonId: Int? = null,
    val lessonName: String,
    val theoreticalUnitNumber: Float,
    val practicalUnitNumber: Float,
    val lessonTypeId: Int,
    val unitType: Boolean,
    // true is for theoretical, false for practical
    val lessonOrientationId: Int? = null,
    val neededPassedUnitsSum: Int? = null,
    val listOfPrerequisites: PrerequisitesList? = null,
    val listOfCorequisites: CorequisitesList? = null,
    val recommendedSemester: Int,
    val lessonState: Boolean? = null
    // null for notTaken (remained), false for failed, true for passed
)
