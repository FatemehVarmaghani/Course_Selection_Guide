package com.example.courseselectionguide.data

data class LessonInfo(
    val lessonId: Int,
    val lessonName: String,
    val theoreticalUnitNumber: Int? = null,
    val practicalUnitNumber: Int? = null,
    val lessonTypeId: Int,
    val unitType: Boolean,
    val lessonOrientationId: Int,
    val neededPassedUnitsSum: Int? = null,
    val listOfPrerequisites: PrerequisitesList? = null,
    val listOfCorequisites: CorequisitesList? = null,
    val recommendedSemester: Int
)
