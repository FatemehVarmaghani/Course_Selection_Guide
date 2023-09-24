package com.example.courseselectionguide.data.tables

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Lessons::class,
            parentColumns = ["lessonId"],
            childColumns = ["selectedLessonId"],
            onUpdate = ForeignKey.CASCADE
        )],
    indices = [Index("selectedLessonId")]
)
data class SelectedLessons(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val selectedLessonId: Int
)