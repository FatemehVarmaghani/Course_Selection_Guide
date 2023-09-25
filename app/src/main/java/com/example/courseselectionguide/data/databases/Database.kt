package com.example.courseselectionguide.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.courseselectionguide.data.daos.CorequisitesDao
import com.example.courseselectionguide.data.daos.LessonOrientationDao
import com.example.courseselectionguide.data.daos.LessonStateDao
import com.example.courseselectionguide.data.daos.LessonTypeDao
import com.example.courseselectionguide.data.daos.LessonsDao
import com.example.courseselectionguide.data.daos.PrerequisitesDao
import com.example.courseselectionguide.data.daos.UserStateDao
import com.example.courseselectionguide.data.tables.Corequisites
import com.example.courseselectionguide.data.tables.LessonOrientation
import com.example.courseselectionguide.data.tables.LessonState
import com.example.courseselectionguide.data.tables.LessonType
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.data.tables.Prerequisites
import com.example.courseselectionguide.data.tables.UserState

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        Lessons::class,
        LessonType::class,
        LessonOrientation::class,
        LessonState::class,
        Prerequisites::class,
        Corequisites::class,
        UserState::class
    ]
)
abstract class MainDatabase : RoomDatabase() {

    //dao
    abstract val lessonsDao: LessonsDao
    abstract val lessonTypeDao: LessonTypeDao
    abstract val lessonOrientationDao: LessonOrientationDao
    abstract val lessonStateDao: LessonStateDao
    abstract val prerequisitesDao: PrerequisitesDao
    abstract val corequisitesDao: CorequisitesDao
    abstract val userStateDao: UserStateDao

    companion object {

        private var dataBase: MainDatabase? = null
        fun getDatabase(context: Context): MainDatabase {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "mainDatabase.db"
                ).allowMainThreadQueries().build()
            }
            return dataBase!!
        }

    }
}