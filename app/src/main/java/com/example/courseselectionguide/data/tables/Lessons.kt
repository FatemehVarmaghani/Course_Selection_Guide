package com.example.courseselectionguide.data.tables

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = LessonType::class,
            parentColumns = ["typeId"],
            childColumns = ["lessonTypeId"],
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LessonOrientation::class,
            parentColumns = ["orientationId"],
            childColumns = ["lessonOrientationId"],
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LessonState::class,
            parentColumns = ["lessonStateId"],
            childColumns = ["lessonState"],
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [Index("lessonTypeId"), Index("lessonOrientationId"), Index("lessonState")]
)
data class Lessons(
    @PrimaryKey(autoGenerate = true)
    val lessonId: Int? = null,
    val lessonName: String,
    val lessonTypeId: Int,
    val unitNumber: Int,
    val isTheoretical: Boolean,
    val lessonOrientationId: Int? = null,
    val neededPassedUnitsSum: Int? = null,
    val recommendedSemester: Int,
    val lessonState: Int,
    val isFixed: Boolean
)
