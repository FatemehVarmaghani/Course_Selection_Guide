package com.example.courseselectionguide.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseselectionguide.R
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.data.CorequisitesList
import com.example.courseselectionguide.data.Lessons
import com.example.courseselectionguide.data.PrerequisitesList
import com.example.courseselectionguide.databinding.Activity2Binding
import com.example.courseselectionguide.databinding.DialogLessonDetailBinding

class Activity2 : AppCompatActivity(), AdapterLessons.ItemEvents {
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
                lessonTypeId = 1,
                recommendedSemester = 1,
                lessonOrientationId = 1
            ),
            Lessons(
                lessonName = "تاریخ فرهنگ و تمدن اسلام و ایران",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                unitType = true,
                lessonTypeId = 2,
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
        val adapter = AdapterLessons(dataList, this)
        binding.recyclerManual.adapter = adapter
        binding.recyclerManual.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_activity2, menu)
        return true
    }

    override fun onItemClicked(lesson: Lessons) {
        //lesson details
        val lessonInfoDialogBinding = DialogLessonDetailBinding.inflate(layoutInflater)
        lessonInfoDialogBinding.infoDialogLessonName.text = lesson.lessonName
        //you can change it later by the use of Room:
        lessonInfoDialogBinding.infoDialogLessonType.text = when(lesson.lessonTypeId) {
            1 -> "عمومی اسلامی"
            2 -> "عمومی"
            3 -> "پایه"
            4 -> "اصلی"
            5 -> "تخصصی"
            6 -> "تمرکز تخصصی"
            7 -> "اختیاری"
            else -> ""
        }
        // for lessons which have both theo and prac units, show float, for others show int:
        if (lesson.unitType == null) {
            lessonInfoDialogBinding.infoDialogTheoreticalUnits.text =
                lesson.theoreticalUnitNumber.toString()
            lessonInfoDialogBinding.infoDialogPracticalUnits.text =
                lesson.practicalUnitNumber.toString()
        } else {
            lessonInfoDialogBinding.infoDialogTheoreticalUnits.text =
                lesson.theoreticalUnitNumber.toInt().toString()
            lessonInfoDialogBinding.infoDialogPracticalUnits.text =
                lesson.practicalUnitNumber.toInt().toString()
        }
        //checking pre and co requisites:
        if(lesson.listOfPrerequisites == null) {
            lessonInfoDialogBinding.infoDialogPrerequisites.text = "ندارد"
        } else {
            lessonInfoDialogBinding.infoDialogPrerequisites.text =
                lesson.listOfPrerequisites.toString() //extract from database (don't forget forEach)
        }
        if(lesson.listOfCorequisites == null) {
            lessonInfoDialogBinding.infoDialogCorequisites.text = "ندارد"
        } else {
            lessonInfoDialogBinding.infoDialogCorequisites.text =
                lesson.listOfCorequisites.toString() // extract from database
        }

        //create & show dialog
        val lessonInfoDialog = AlertDialog.Builder(this)
        lessonInfoDialog.setView(lessonInfoDialogBinding.root)
        lessonInfoDialog.create().show()
    }

    override fun onOptionsIconClicked() {
        Toast.makeText(this, "on options clicked", Toast.LENGTH_SHORT).show()
    }
}