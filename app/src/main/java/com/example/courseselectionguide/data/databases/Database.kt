package com.example.courseselectionguide.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.courseselectionguide.data.daos.LessonsDao
import com.example.courseselectionguide.data.data_classes.CorequisitesList
import com.example.courseselectionguide.data.data_classes.LessonOrientation
import com.example.courseselectionguide.data.data_classes.LessonState
import com.example.courseselectionguide.data.data_classes.LessonType
import com.example.courseselectionguide.data.data_classes.Lessons
import com.example.courseselectionguide.data.data_classes.PrerequisitesList
import com.example.courseselectionguide.data.data_classes.UserState

@Database(
    version = 1,
    exportSchema = false,
    entities = [Lessons::class,
        LessonType::class,
        LessonOrientation::class,
        LessonState::class,
        UserState::class]
)
abstract class LessonDatabase : RoomDatabase() {

    //dao
    abstract val lessonsDao: LessonsDao
    abstract val lessonStateDao: LessonsDao
    abstract val lessonTypeDao: LessonsDao
    abstract val lessonOrientationDao: LessonsDao
    abstract val userStateDao: LessonsDao

    companion object {
        private var database: LessonDatabase? = null
        fun getDatabase(context: Context): LessonDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    LessonDatabase::class.java,
                    "appDatabase.db"
                ).allowMainThreadQueries().build()
            }
            return database!!
        }
    }
}