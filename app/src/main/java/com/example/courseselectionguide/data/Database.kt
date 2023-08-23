package com.example.courseselectionguide.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [Lessons::class,
        RemainedLessons::class,
        SelectedLessons::class,
        RecommendedLessons::class,
        PassedLessons::class,
        FailedLessons::class,
        PrerequisitesList::class,
        CorequisitesList::class,
        LessonType::class,
        LessonOrientation::class]
)
abstract class LessonDatabase : RoomDatabase() {
    abstract val databaseDao: RemainedLessonsDao

    //don't forget other daos
    companion object {
        private var Database: LessonDatabase? = null
        fun getDatabase(context: Context): LessonDatabase {
            var instance = Database
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LessonDatabase::class.java,
                    "myDatabase.db"
                ).build()
            }
            return instance
        }
    }
}