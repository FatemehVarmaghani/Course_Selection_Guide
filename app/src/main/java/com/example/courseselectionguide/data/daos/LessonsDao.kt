package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.courseselectionguide.data.data_classes.LessonOrientation
import com.example.courseselectionguide.data.data_classes.LessonState
import com.example.courseselectionguide.data.data_classes.LessonType
import com.example.courseselectionguide.data.data_classes.Lessons
import com.example.courseselectionguide.data.data_classes.UserState

interface BaseDao<T> {
    @Insert
    fun insertAll(vararg obj: T)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(vararg obj: T)

    //to insert the whole table
    @Insert
    fun insertAll(list: ArrayList<T>)
}

@Dao
interface LessonsDao : BaseDao<Lessons> {
    //for remained
    @Query("select * from Lessons where lessonState = 1")
    fun getRemainedList(): ArrayList<Lessons>

    @Query("select * from Lessons where lessonState = 1 and lessonName like '%' || :searching || '%'")
    fun searchInRemained(searching: String): ArrayList<Lessons>

    //for passed
    @Query("select * from Lessons where lessonState = 2")
    fun getPassedList(): ArrayList<Lessons>

    @Query("select * from Lessons where lessonState = 2 and lessonName like '%' || :searching || '%'")
    fun searchInPassed(searching: String): ArrayList<Lessons>

    //for failed
    @Query("select * from Lessons where lessonState = 3")
    fun getFailedList(): ArrayList<Lessons>

    @Query("select * from Lessons where lessonState = 3 and lessonName like '%' || :searching || '%'")
    fun searchInFailed(searching: String): ArrayList<Lessons>

    //for selected
    @Query("select * from Lessons where lessonState = 4")
    fun getSelectedList(): ArrayList<Lessons>

    //for recommended
    @Query("select * from Lessons where lessonState = 5")
    fun getRecommendedList(): ArrayList<Lessons>

    //for lesson type
    @Query("select typeName from Lessons inner join LessonType on Lessons.lessonTypeId = LessonType.typeId where typeId = :typeId")
    fun getLessonType(typeId: Int): String

    //for user's state
    @Query("select stateName from Lessons inner join UserState on Lessons.lessonState = UserState.stateId where stateId = :stateId")
    fun getUserState(stateId: String): String

    //for lesson state
    @Query("select lessonStateName from Lessons inner join LessonState on Lessons.lessonState = LessonState.lessonStateId where lessonStateId = :stateId")
    fun getLessonState(stateId: String): String

    //for lesson orientation
    @Query("select orientationName from Lessons inner join LessonOrientation on Lessons.lessonOrientationId = LessonOrientation.orientationId where orientationId = :orientationId")
    fun getLessonOrientation(orientationId: Int): String
}

@Dao
interface LessonStateDao : BaseDao<LessonState>

@Dao
interface LessonTypeDao : BaseDao<LessonType>

@Dao
interface LessonOrientationDao : BaseDao<LessonOrientation>

@Dao
interface UserStateDao : BaseDao<UserState>