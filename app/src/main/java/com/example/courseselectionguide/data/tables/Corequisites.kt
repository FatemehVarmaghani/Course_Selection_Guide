package com.example.courseselectionguide.data.tables

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Lessons::class,
        parentColumns = ["lessonId"],
        childColumns = ["mainLessonId"],
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [Index("mainLessonId")]
)
data class Corequisites(
    @PrimaryKey(autoGenerate = true)
    val corequisiteId: Int? = null,
    val mainLessonId: Int,
    val corequisiteLessonId: Int
)