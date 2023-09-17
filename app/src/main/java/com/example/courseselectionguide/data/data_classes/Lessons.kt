package com.example.courseselectionguide.data.data_classes

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
    val unitType: Boolean? = null,
    // true is for theoretical, false for practical, null for both (like pe)
    val lessonOrientationId: Int? = null,
    val neededPassedUnitsSum: Int? = null,
    val listOfPrerequisites: PrerequisitesList? = null,
    val listOfCorequisites: CorequisitesList? = null,
    val recommendedSemester: Int,
    val lessonState: Int? = null //it's nullable only for now... you can change it later
)
