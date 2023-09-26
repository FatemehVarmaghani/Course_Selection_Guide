package com.example.courseselectionguide.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.data.daos.CorequisitesDao
import com.example.courseselectionguide.data.daos.LessonOrientationDao
import com.example.courseselectionguide.data.daos.LessonStateDao
import com.example.courseselectionguide.data.daos.LessonTypeDao
import com.example.courseselectionguide.data.daos.LessonsDao
import com.example.courseselectionguide.data.daos.PrerequisitesDao
import com.example.courseselectionguide.data.daos.UserStateDao
import com.example.courseselectionguide.data.databases.MainDatabase
import com.example.courseselectionguide.data.tables.Corequisites
import com.example.courseselectionguide.data.tables.LessonOrientation
import com.example.courseselectionguide.data.tables.LessonState
import com.example.courseselectionguide.data.tables.LessonType
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.data.tables.Prerequisites
import com.example.courseselectionguide.data.tables.UserState
import com.example.courseselectionguide.databinding.ActivityMainBinding
import com.example.courseselectionguide.fragments.RecommendationFragment
import com.example.courseselectionguide.fragments.SelectedLessonsFragment
import com.example.courseselectionguide.fragments.StateFragment
import java.lang.Exception

const val PRIMITIVE_DATA = "primitive_data"
const val FIRST_RUNNING = "first_running"
const val CURRENT_SEMESTER = "current_semester"
const val AVERAGE = "average"
const val HAS_FAILED = "has_failed"
const val IS_SENIOR = "is_senior"
const val USER_STATE = "user_state"

lateinit var sharedPref: SharedPreferences
lateinit var lessonsDao: LessonsDao
lateinit var lessonTypeDao: LessonTypeDao
lateinit var lessonOrientationDao: LessonOrientationDao
lateinit var lessonStateDao: LessonStateDao
lateinit var prerequisitesDao: PrerequisitesDao
lateinit var corequisitesDao: CorequisitesDao
lateinit var userStateDao: UserStateDao

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //checking entry activity
        sharedPref = this.getSharedPreferences(PRIMITIVE_DATA, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(FIRST_RUNNING, true)) {
            firstRun()
            val intent = Intent(this, DataEntryActivity::class.java)
            startActivity(intent)
        }

        //transactions for main fragments
        starter()
        binding.mainBottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.bottom_menu_State -> {
                    transactionTo(R.id.main_container_frame, StateFragment())
                    true
                }

                R.id.bottom_menu_recommendation -> {
                    transactionTo(R.id.main_container_frame, RecommendationFragment())
                    true
                }

                R.id.bottom_menu_selected -> {
                    transactionTo(R.id.main_container_frame, SelectedLessonsFragment())
                    true
                }

                else -> false

            }

        }
        binding.mainBottomNavigation.setOnItemReselectedListener { }
    }

    private fun firstRun() {
        try {

            //lesson types
            lessonTypeDao = MainDatabase.getDatabase(this).lessonTypeDao
            val lessonTypeList = arrayListOf(
                LessonType(typeName = getString(R.string.islamic_general)),
                LessonType(typeName = getString(R.string.general)),
                LessonType(typeName = getString(R.string.basic)),
                LessonType(typeName = getString(R.string.main)),
                LessonType(typeName = getString(R.string.specialized)),
                LessonType(typeName = getString(R.string.focused_specialized)),
                LessonType(typeName = getString(R.string.optional))
            )
            lessonTypeDao.insertAll(lessonTypeList)

            //lesson orientations
            lessonOrientationDao = MainDatabase.getDatabase(this).lessonOrientationDao
            val lessonOrientationList = arrayListOf(
                LessonOrientation(
                    orientationName = getString(R.string.theoretical_foundations_of_islam),
                    allowedUnitsSum = 4
                ),
                LessonOrientation(
                    orientationName = getString(R.string.islamic_ethics),
                    allowedUnitsSum = 2
                ),
                LessonOrientation(
                    orientationName = getString(R.string.islamic_revolution),
                    allowedUnitsSum = 2
                ),
                LessonOrientation(
                    orientationName = getString(R.string.islamic_history_and_civilization),
                    allowedUnitsSum = 2
                ),
                LessonOrientation(
                    orientationName = getString(R.string.familiarity_with_islamic_sources),
                    allowedUnitsSum = 2
                ),
                LessonOrientation(orientationName = getString(R.string.software_systems)),
                LessonOrientation(
                    orientationName = getString(R.string.information_systems),
                    allowedUnitsSum = 12
                ),
                LessonOrientation(
                    orientationName = getString(R.string.computer_games),
                    allowedUnitsSum = 3
                ),
                LessonOrientation(
                    orientationName = getString(R.string.artificial_intelligence),
                    allowedUnitsSum = 3
                )
            )
            lessonOrientationDao.insertAll(lessonOrientationList)

            //lesson states
            lessonStateDao = MainDatabase.getDatabase(this).lessonStateDao
            val lessonStateList = arrayListOf(
                LessonState(lessonStateName = getString(R.string.remained_lessons)),
                LessonState(lessonStateName = getString(R.string.passed_lessons)),
                LessonState(lessonStateName = getString(R.string.failed_lessons)),
                LessonState(lessonStateName = getString(R.string.selected_lessons))
            )
            lessonStateDao.insertAll(lessonStateList)

            //lessons
            lessonsDao = MainDatabase.getDatabase(this).lessonsDao
            val lessonsList = arrayListOf(
                Lessons(
                    lessonName = "ریاضی عمومی 1",
                    unitNumber = 3,
                    lessonTypeId = 3,
                    isTheoretical = true,
                    recommendedSemester = 1,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "فیزیک 1",
                    unitNumber = 3,
                    lessonTypeId = 3,
                    isTheoretical = true,
                    recommendedSemester = 1,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "فارسی عمومی",
                    unitNumber = 3,
                    lessonTypeId = 2,
                    isTheoretical = true,
                    recommendedSemester = 1,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "زبان عمومی",
                    unitNumber = 3,
                    lessonTypeId = 2,
                    isTheoretical = true,
                    recommendedSemester = 1,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "مبانی کامپیوتر و برنامه سازی",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 1,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "اندیشه اسلامی 1",
                    unitNumber = 2,
                    lessonTypeId = 1,
                    isTheoretical = true,
                    recommendedSemester = 1,
                    lessonOrientationId = 1,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "تاریخ فرهنگ و تمدن اسلام و ایران",
                    unitNumber = 2,
                    lessonTypeId = 2,
                    isTheoretical = true,
                    recommendedSemester = 1,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "فیزیک 2",
                    unitNumber = 3,
                    lessonTypeId = 3,
                    isTheoretical = true,
                    recommendedSemester = 2,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "ریاضی عمومی 2",
                    unitNumber = 3,
                    lessonTypeId = 3,
                    isTheoretical = true,
                    recommendedSemester = 2,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "ریاضیات گسسته",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 2,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "معادلات دیفرانسیل",
                    unitNumber = 3,
                    lessonTypeId = 3,
                    isTheoretical = true,
                    recommendedSemester = 2,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "اندیشه اسلامی 2",
                    unitNumber = 2,
                    lessonTypeId = 1,
                    isTheoretical = true,
                    recommendedSemester = 2,
                    lessonOrientationId = 1,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "برنامه سازی پیشرفته",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 2,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "کارگاه کامپیوتر",
                    unitNumber = 1,
                    lessonTypeId = 3,
                    isTheoretical = false,
                    recommendedSemester = 2,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "تربیت بدنی",
                    unitNumber = 1,
                    lessonTypeId = 2,
                    isTheoretical = false,
                    recommendedSemester = 2,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "مدارهای الکتریکی",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 3,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آزمایشگاه فیزیک 2",
                    unitNumber = 1,
                    lessonTypeId = 3,
                    isTheoretical = false,
                    recommendedSemester = 3,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آمار و احتمال مهندسی",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 3,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "ساختمان داده",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 3,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "مدارهای منطقی",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 3,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "ریاضیات مهندسی",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 3,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "انقلاب اسلامی ایران",
                    unitNumber = 2,
                    lessonTypeId = 1,
                    isTheoretical = true,
                    recommendedSemester = 3,
                    lessonOrientationId = 3,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "زبان تخصصی",
                    unitNumber = 2,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 3,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "نظریه زبان ها و ماشین ها",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 4,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "طراحی الگوریتم",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 4,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "سیگنال ها و سیستم ها",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 4,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "معماری کامپیوتر",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 4,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "پایگاه داده",
                    unitNumber = 3,
                    lessonTypeId = 5,
                    isTheoretical = true,
                    recommendedSemester = 4,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "روش پژوهش و ارائه",
                    unitNumber = 2,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 4,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آئین زندگی (اخلاق کاربردی)",
                    unitNumber = 2,
                    lessonTypeId = 1,
                    isTheoretical = true,
                    recommendedSemester = 4,
                    lessonOrientationId = 2,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "تحلیل و طراحی سیستم ها",
                    unitNumber = 3,
                    lessonTypeId = 5,
                    isTheoretical = true,
                    recommendedSemester = 5,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "اصول طراحی کامپایلرها",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 5,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "ریزپردازنده و زبان اسمبلی",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 5,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "طراحی کامپیوتری سیستم های دیجیتال",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 5,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "سیستم های عامل",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 5,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آزمایشگاه سیستم های عامل",
                    unitNumber = 1,
                    lessonTypeId = 4,
                    isTheoretical = false,
                    recommendedSemester = 5,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آزمایشگاه مدارهای منطقی و معماری کامپیوتر",
                    unitNumber = 1,
                    lessonTypeId = 4,
                    isTheoretical = false,
                    recommendedSemester = 5,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "تاریخ تحلیل صدر اسلام",
                    unitNumber = 2,
                    lessonTypeId = 1,
                    isTheoretical = true,
                    lessonOrientationId = 4,
                    recommendedSemester = 5,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "مهندسی نرم افزار",
                    unitNumber = 3,
                    lessonTypeId = 5,
                    isTheoretical = true,
                    recommendedSemester = 6,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "هوش مصنوعی و سیستم خبره",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 6,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "شبکه های کامپیوتری",
                    unitNumber = 3,
                    lessonTypeId = 4,
                    isTheoretical = true,
                    recommendedSemester = 6,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آزمایشگاه ریزپردازنده",
                    unitNumber = 1,
                    lessonTypeId = 4,
                    isTheoretical = false,
                    recommendedSemester = 6,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آزمایشگاه شبکه های کامپیوتر",
                    unitNumber = 1,
                    lessonTypeId = 4,
                    isTheoretical = false,
                    recommendedSemester = 6,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "دانش خانواده و جمعیت",
                    unitNumber = 2,
                    lessonTypeId = 2,
                    isTheoretical = true,
                    recommendedSemester = 6,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "ورزش 1",
                    unitNumber = 1,
                    lessonTypeId = 2,
                    isTheoretical = false,
                    recommendedSemester = 6,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "مهندسی اینترنت",
                    unitNumber = 3,
                    lessonTypeId = 5,
                    isTheoretical = true,
                    recommendedSemester = 7,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "طراحی زبان های برنامه سازی",
                    unitNumber = 3,
                    lessonTypeId = 5,
                    isTheoretical = true,
                    recommendedSemester = 7,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "تفسیر موضوعی نهج البلاغه",
                    unitNumber = 2,
                    lessonTypeId = 1,
                    isTheoretical = true,
                    recommendedSemester = 7,
                    lessonOrientationId = 5,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "پروژه نرم افزار",
                    unitNumber = 3,
                    lessonTypeId = 5,
                    isTheoretical = false,
                    recommendedSemester = 8,
                    lessonState = 1,
                    neededPassedUnitsSum = 100,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "کارآموزی",
                    unitNumber = 1,
                    lessonTypeId = 5,
                    isTheoretical = false,
                    recommendedSemester = 8,
                    lessonState = 1,
                    neededPassedUnitsSum = 80,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آشنایی با قرآن",
                    unitNumber = 1,
                    lessonTypeId = 2,
                    isTheoretical = true,
                    recommendedSemester = 8,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "اندیشه ها و وصایای امام",
                    unitNumber = 1,
                    lessonTypeId = 2,
                    isTheoretical = true,
                    recommendedSemester = 8,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "تاریخ فرهنگ و تمدن اسلامی",
                    unitNumber = 2,
                    lessonTypeId = 1,
                    isTheoretical = true,
                    recommendedSemester = 5,
                    lessonOrientationId = 4,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "تفسیر موضوعی قرآن",
                    unitNumber = 2,
                    lessonTypeId = 1,
                    isTheoretical = true,
                    recommendedSemester = 7,
                    lessonOrientationId = 5,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "پیاده سازی سیستم پایگاه داده",
                    unitNumber = 3,
                    lessonTypeId = 6,
                    isTheoretical = true,
                    recommendedSemester = 6,
                    lessonOrientationId = 7,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "مبانی داده کاوی",
                    unitNumber = 3,
                    lessonTypeId = 6,
                    isTheoretical = true,
                    recommendedSemester = 7,
                    lessonOrientationId = 7,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "مبانی بازیابی اطلاعات و جستجوی وب",
                    unitNumber = 3,
                    lessonTypeId = 6,
                    isTheoretical = true,
                    recommendedSemester = 7,
                    lessonOrientationId = 7,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "سیستم های اطلاعات مدیریت",
                    unitNumber = 3,
                    lessonTypeId = 6,
                    isTheoretical = true,
                    recommendedSemester = 8,
                    lessonOrientationId = 7,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "گرافیک کامپیوتری",
                    unitNumber = 3,
                    lessonTypeId = 7,
                    isTheoretical = true,
                    recommendedSemester = 7,
                    lessonOrientationId = 8,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "اصول رباتیکز",
                    unitNumber = 3,
                    lessonTypeId = 7,
                    isTheoretical = true,
                    recommendedSemester = 8,
                    lessonOrientationId = 9,
                    lessonState = 1,
                    isFixed = true
                ),
                Lessons(
                    lessonName = "آزمایشگاه مهندسی نرم افزار",
                    unitNumber = 1,
                    lessonTypeId = 7,
                    isTheoretical = false,
                    recommendedSemester = 7,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "آزمایشگاه اصول طراحی کامپایلر",
                    unitNumber = 1,
                    lessonTypeId = 7,
                    isTheoretical = false,
                    recommendedSemester = 8,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "آزمایشگاه پایگاه داده",
                    unitNumber = 1,
                    lessonTypeId = 7,
                    isTheoretical = false,
                    recommendedSemester = 7,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "آزمایشگاه مدارهای الکتریکی",
                    unitNumber = 1,
                    lessonTypeId = 7,
                    isTheoretical = false,
                    recommendedSemester = 8,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "آزمایشگاه اصول رباتیکز",
                    unitNumber = 1,
                    lessonTypeId = 7,
                    isTheoretical = false,
                    recommendedSemester = 7,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "آزمایشگاه گرافیک کامپیوتری",
                    unitNumber = 1,
                    lessonTypeId = 7,
                    isTheoretical = false,
                    recommendedSemester = 8,
                    lessonState = 1,
                    isFixed = false
                ),
                Lessons(
                    lessonName = "کارگاه برنامه نویسی مت لب",
                    unitNumber = 1,
                    lessonTypeId = 7,
                    isTheoretical = false,
                    recommendedSemester = 7,
                    lessonState = 1,
                    isFixed = false
                ),
            )
            lessonsDao.insertAll(lessonsList)

            //prerequisites
            prerequisitesDao = MainDatabase.getDatabase(this).prerequisitesDao
            val prerequisitesList = arrayListOf(
                Prerequisites(mainLessonId = 8, prerequisiteLessonId = 2),
                Prerequisites(mainLessonId = 9, prerequisiteLessonId = 1),
                Prerequisites(mainLessonId = 11, prerequisiteLessonId = 1),
                Prerequisites(mainLessonId = 12, prerequisiteLessonId = 6),
                Prerequisites(mainLessonId = 13, prerequisiteLessonId = 5),
                Prerequisites(mainLessonId = 14, prerequisiteLessonId = 5),
                Prerequisites(mainLessonId = 16, prerequisiteLessonId = 11),
                Prerequisites(mainLessonId = 17, prerequisiteLessonId = 8),
                Prerequisites(mainLessonId = 18, prerequisiteLessonId = 9),
                Prerequisites(mainLessonId = 19, prerequisiteLessonId = 10),
                Prerequisites(mainLessonId = 19, prerequisiteLessonId = 13),
                Prerequisites(mainLessonId = 21, prerequisiteLessonId = 9),
                Prerequisites(mainLessonId = 21, prerequisiteLessonId = 11),
                Prerequisites(mainLessonId = 23, prerequisiteLessonId = 4),
                Prerequisites(mainLessonId = 24, prerequisiteLessonId = 19),
                Prerequisites(mainLessonId = 25, prerequisiteLessonId = 19),
                Prerequisites(mainLessonId = 26, prerequisiteLessonId = 21),
                Prerequisites(mainLessonId = 27, prerequisiteLessonId = 20),
                Prerequisites(mainLessonId = 28, prerequisiteLessonId = 19),
                Prerequisites(mainLessonId = 29, prerequisiteLessonId = 23),
                Prerequisites(mainLessonId = 31, prerequisiteLessonId = 13),
                Prerequisites(mainLessonId = 32, prerequisiteLessonId = 19),
                Prerequisites(mainLessonId = 33, prerequisiteLessonId = 27),
                Prerequisites(mainLessonId = 34, prerequisiteLessonId = 27),
                Prerequisites(mainLessonId = 35, prerequisiteLessonId = 19),
                Prerequisites(mainLessonId = 35, prerequisiteLessonId = 27),
                Prerequisites(mainLessonId = 37, prerequisiteLessonId = 20),
                Prerequisites(mainLessonId = 39, prerequisiteLessonId = 31),
                Prerequisites(mainLessonId = 40, prerequisiteLessonId = 19),
                Prerequisites(mainLessonId = 41, prerequisiteLessonId = 35),
                Prerequisites(mainLessonId = 42, prerequisiteLessonId = 33),
                Prerequisites(mainLessonId = 45, prerequisiteLessonId = 15),
                Prerequisites(mainLessonId = 46, prerequisiteLessonId = 41),
                Prerequisites(mainLessonId = 47, prerequisiteLessonId = 32),
                Prerequisites(mainLessonId = 55, prerequisiteLessonId = 28),
                Prerequisites(mainLessonId = 56, prerequisiteLessonId = 28),
                Prerequisites(mainLessonId = 56, prerequisiteLessonId = 19),
                Prerequisites(mainLessonId = 57, prerequisiteLessonId = 25),
                Prerequisites(mainLessonId = 58, prerequisiteLessonId = 31),
                Prerequisites(mainLessonId = 59, prerequisiteLessonId = 13),
                Prerequisites(mainLessonId = 60, prerequisiteLessonId = 13)
            )
            prerequisitesDao.insertAll(prerequisitesList)

            //corequisites
            corequisitesDao = MainDatabase.getDatabase(this).corequisitesDao
            val corequisitesList = arrayListOf(
                Corequisites(mainLessonId = 10, corequisiteLessonId = 1),
                Corequisites(mainLessonId = 10, corequisiteLessonId = 5),
                Corequisites(mainLessonId = 20, corequisiteLessonId = 10),
                Corequisites(mainLessonId = 36, corequisiteLessonId = 35),
                Corequisites(mainLessonId = 37, corequisiteLessonId = 27),
                Corequisites(mainLessonId = 43, corequisiteLessonId = 41),
                Corequisites(mainLessonId = 46, corequisiteLessonId = 28),
                Corequisites(mainLessonId = 61, corequisiteLessonId = 31),
                Corequisites(mainLessonId = 62, corequisiteLessonId = 32),
                Corequisites(mainLessonId = 63, corequisiteLessonId = 28),
                Corequisites(mainLessonId = 64, corequisiteLessonId = 16),
                Corequisites(mainLessonId = 65, corequisiteLessonId = 60),
                Corequisites(mainLessonId = 66, corequisiteLessonId = 59),
                Corequisites(mainLessonId = 67, corequisiteLessonId = 26)
            )
            corequisitesDao.insertAll(corequisitesList)

            //user state
            userStateDao = MainDatabase.getDatabase(this).userStateDao
            val userStateList = arrayListOf(
                UserState(stateName = getString(R.string.excellent)),
                UserState(stateName = getString(R.string.normal)),
                UserState(stateName = getString(R.string.senior)),
                UserState(stateName = getString(R.string.conditional))
            )
            userStateDao.insertAll(userStateList)

        } catch (e: Exception) {
            Toast.makeText(this, "catch!", Toast.LENGTH_SHORT).show()
        }

    }

    // to open State Fragment when the app is opened
    private fun starter() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container_frame, StateFragment())
        transaction.commit()
    }

    //to load fragments when bottom navigation icon's are active
    private fun transactionTo(containerId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.commit()
    }
}