package com.example.courseselectionguide.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

//remained lessons table
@Entity(
    foreignKeys = [(ForeignKey(
        Lessons::class,
        arrayOf("lessonId"),
        arrayOf("lessonId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ))]
)
data class RemainedLessons(
    @PrimaryKey(autoGenerate = true)
    val remainedLessonId: Int? = null,
    // TODO: make this field unique
    val lessonId: Int
)

//selected lessons table
@Entity(
    foreignKeys = [(ForeignKey(
        Lessons::class,
        arrayOf("lessonId"),
        arrayOf("lessonId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ))]
)
data class SelectedLessons(
    @PrimaryKey(autoGenerate = true)
    val selectedLessonId: Int? = null,
    val lessonId: Int
)

//recommended lessons table
@Entity(
    foreignKeys = [(ForeignKey(
        Lessons::class,
        arrayOf("lessonId"),
        arrayOf("lessonId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ))]
)
data class RecommendedLessons(
    @PrimaryKey(autoGenerate = true)
    val recommendedLessonId: Int? = null,
    val lessonId: Int
)

//passed lessons table
@Entity(
    foreignKeys = [(ForeignKey(
        Lessons::class,
        arrayOf("lessonId"),
        arrayOf("lessonId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ))]
)
data class PassedLessons(
    @PrimaryKey(autoGenerate = true)
    val passedLessonId: Int? = null,
    val lessonId: Int
)

//failed lessons table
@Entity(
    foreignKeys = [(ForeignKey(
        Lessons::class,
        arrayOf("lessonId"),
        arrayOf("lessonId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ))]
)
data class FailedLessons(
    @PrimaryKey(autoGenerate = true)
    val failedLessonId: Int? = null,
    val lessonId: Int
)