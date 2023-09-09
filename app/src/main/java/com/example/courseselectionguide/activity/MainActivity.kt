package com.example.courseselectionguide.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.data.CorequisitesList
import com.example.courseselectionguide.data.Lessons
import com.example.courseselectionguide.data.PrerequisitesList
import com.example.courseselectionguide.databinding.ActivityMainBinding
import com.example.courseselectionguide.fragments.RecommendationFragment
import com.example.courseselectionguide.fragments.SelectedLessonsFragment
import com.example.courseselectionguide.fragments.StateFragment

lateinit var sharedPref: SharedPreferences
lateinit var dataList: ArrayList<Lessons>

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //checking entry activity
        sharedPref = this.getSharedPreferences("primitive_data", Context.MODE_PRIVATE)
        val notFirstRun = sharedPref.getBoolean("data_entry_shown", false)
        if (!notFirstRun) {
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

        //data from database
        dataList = arrayListOf(
            Lessons(
                lessonName = "ریاضی عمومی 1",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 3,
                recommendedSemester = 1
            ),
            Lessons(
                lessonName = "فیزیک 1",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 3,
                recommendedSemester = 1
            ),
            Lessons(
                lessonName = "فارسی عمومی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 2,
                recommendedSemester = 1
            ),
            Lessons(
                lessonName = "زبان عمومی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 2,
                recommendedSemester = 1
            ),
            Lessons(
                lessonName = "مبانی کامپیوتر و برنامه سازی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 4,
                recommendedSemester = 1
            ),
            Lessons(
                lessonName = "اندیش اسلامی 1",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 2,
                recommendedSemester = 1,
                lessonOrientationId = 1
            ),
            Lessons(
                lessonName = "تاریخ فرهنگ و تمدن اسلام و ایران",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 1,
                recommendedSemester = 1
            ),
            Lessons(
                lessonName = "فیزیک 2",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 3,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(2))
            ),
            Lessons(
                lessonName = "ریاضی عمومی 2",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 3,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(1))
            ),
            Lessons(
                lessonName = "ریاضیات گسسته",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 4,
                recommendedSemester = 2,
                listOfCorequisites = CorequisitesList(arrayListOf(1, 5))
            )
        )
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