package com.example.courseselectionguide.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseselectionguide.R
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.data.CorequisitesList
import com.example.courseselectionguide.data.Lessons
import com.example.courseselectionguide.data.PrerequisitesList
import com.example.courseselectionguide.data.SelectedLessons
import com.example.courseselectionguide.databinding.Activity2Binding

class Activity2 : AppCompatActivity() {
    private lateinit var binding: Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        val listTitle = "آزمایشی"
        binding.toolbarManual.title = listTitle
        setSupportActionBar(binding.toolbarManual)

        //options menu listener
        binding.toolbarManual.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filter_lessons -> {
                    Toast.makeText(this, "filter done!", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        //data for recyclerView
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

        //recyclerView for lessons
        val adapter = AdapterLessons(dataList)
        binding.recyclerManual.adapter = adapter
        binding.recyclerManual.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_activity2, menu)
        return true
    }
}