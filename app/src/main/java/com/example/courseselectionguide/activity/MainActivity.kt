package com.example.courseselectionguide.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.data.daos.LessonOrientationDao
import com.example.courseselectionguide.data.daos.LessonStateDao
import com.example.courseselectionguide.data.daos.LessonTypeDao
import com.example.courseselectionguide.data.daos.LessonsDao
import com.example.courseselectionguide.data.daos.UserStateDao
import com.example.courseselectionguide.data.data_classes.CorequisitesList
import com.example.courseselectionguide.data.data_classes.LessonOrientation
import com.example.courseselectionguide.data.data_classes.LessonState
import com.example.courseselectionguide.data.data_classes.LessonType
import com.example.courseselectionguide.data.data_classes.Lessons
import com.example.courseselectionguide.data.data_classes.PrerequisitesList
import com.example.courseselectionguide.data.data_classes.UserState
import com.example.courseselectionguide.data.databases.LessonDatabase
import com.example.courseselectionguide.databinding.ActivityMainBinding
import com.example.courseselectionguide.fragments.RecommendationFragment
import com.example.courseselectionguide.fragments.SelectedLessonsFragment
import com.example.courseselectionguide.fragments.StateFragment

lateinit var sharedPref: SharedPreferences
lateinit var lessonsList: ArrayList<Lessons>
lateinit var lessonsDao: LessonsDao
lateinit var lessonStateList: ArrayList<LessonState>
lateinit var lessonStateDao: LessonStateDao
lateinit var lessonTypeList: ArrayList<LessonType>
lateinit var lessonTypeDao: LessonTypeDao
lateinit var lessonOrientationList: ArrayList<LessonOrientation>
lateinit var lessonOrientationDao: LessonOrientationDao
lateinit var userStateList: ArrayList<UserState>
lateinit var userStateDao: UserStateDao

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //checking entry activity
        sharedPref = this.getSharedPreferences("primitive_data", Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("first_running", true)) {
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
        lessonsDao = LessonDatabase.getDatabase(this).lessonsDao

        //lessons
        lessonsList = arrayListOf(
            Lessons(
                lessonName = "ریاضی عمومی 1",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "فیزیک 1",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "فارسی عمومی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 2,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "زبان عمومی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 2,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "مبانی کامپیوتر و برنامه سازی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "اندیش اسلامی 1",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 1,
                unitType = true,
                recommendedSemester = 1,
                lessonOrientationId = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "تاریخ فرهنگ و تمدن اسلام و ایران",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 2,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "فیزیک 2",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(2)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "ریاضی عمومی 2",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(1)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "ریاضیات گسسته",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 2,
                listOfCorequisites = CorequisitesList(arrayListOf(1, 5)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "معادلات دیفرانسیل",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(1)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "اندیشه اسلامی 2",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 1,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(6)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "برنامه سازی پیشرفته",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(5)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "کارگاه کامپیوتر",
                theoreticalUnitNumber = 0f,
                practicalUnitNumber = 1f,
                lessonTypeId = 3,
                unitType = false,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(5)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "تربیت بدنی",
                theoreticalUnitNumber = 0.5f,
                practicalUnitNumber = 0.5f,
                lessonTypeId = 2,
                recommendedSemester = 2,
                lessonState = 1
            ),
            Lessons(
                lessonName = "مدارهای الکتریکی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(11)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "آزمایشگاه فیزیک 2",
                theoreticalUnitNumber = 0f,
                practicalUnitNumber = 1f,
                lessonTypeId = 3,
                unitType = false,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(8)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "آمار و احتمال مهندسی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(9)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "ساختمان داده",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(10, 13)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "مدارهای منطقی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfCorequisites = CorequisitesList(arrayListOf(10)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "ریاضیات مهندسی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(9, 11)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "انقلاب اسلامی",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 1,
                unitType = true,
                recommendedSemester = 3,
                lessonState = 1
            ),
            Lessons(
                lessonName = "زبان تخصصی",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(4)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "نظریه زبان ها و ماشین  ها",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(19)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "طراحی الگوریتم",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(19)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "سیگنال ها و سیستم ها",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(21)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "معماری کامپیوتر",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(20)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "پایگاه داده",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 5,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(19)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "روش پژوهش و ارائه",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(23)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "آئین زندگی (اخلاق کاربردی)",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 1,
                unitType = true,
                recommendedSemester = 4,
                lessonState = 1
            )
        )
        lessonsDao.insertAll(lessonsList)

        //lesson state
        lessonStateList = arrayListOf(
            LessonState(lessonStateName = getString(R.string.remained)),
            LessonState(lessonStateName = getString(R.string.passed)),
            LessonState(lessonStateName = getString(R.string.failed)),
            LessonState(lessonStateName = getString(R.string.selected)),
            LessonState(lessonStateName = getString(R.string.recommended))
        )
        lessonStateDao.insertAll(lessonStateList)

        //lesson type
        lessonTypeList = arrayListOf(
            LessonType(typeName = getString(R.string.islamic_general)),
            LessonType(typeName = getString(R.string.general)),
            LessonType(typeName = getString(R.string.basic)),
            LessonType(typeName = getString(R.string.main)),
            LessonType(typeName = getString(R.string.specialized)),
            LessonType(typeName = getString(R.string.focused_specialized)),
            LessonType(typeName = getString(R.string.optional))
        )
        lessonTypeDao.insertAll(lessonTypeList)

        //lesson orientation
        lessonOrientationList = arrayListOf(
            LessonOrientation(orientationName = getString(R.string.theoretical_foundations_of_islam)),
            LessonOrientation(orientationName = getString(R.string.islamic_ethics)),
            LessonOrientation(orientationName = getString(R.string.islamic_revolution)),
            LessonOrientation(orientationName = getString(R.string.islamic_history_and_civilization)),
            LessonOrientation(orientationName = getString(R.string.familiarity_with_islamic_sources)),
            LessonOrientation(orientationName = getString(R.string.software_systems)),
            LessonOrientation(orientationName = getString(R.string.information_systems)),
            LessonOrientation(orientationName = getString(R.string.computer_games)),
            LessonOrientation(orientationName = getString(R.string.artificial_intelligence))
        )
        lessonOrientationDao.insertAll(lessonOrientationList)

        //user state
        userStateList = arrayListOf(
            UserState(stateName = getString(R.string.excellent)),
            UserState(stateName = getString(R.string.normal)),
            UserState(stateName = getString(R.string.senior)),
        )
        userStateDao.insertAll(userStateList)

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