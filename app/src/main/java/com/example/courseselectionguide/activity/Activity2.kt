package com.example.courseselectionguide.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.courseselectionguide.R
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.classes.UtilityClass
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.databinding.Activity2Binding
import com.example.courseselectionguide.databinding.DialogLessonDetailBinding
import com.example.courseselectionguide.fragments.FilterDialog

class Activity2 : AppCompatActivity(), AdapterLessons.ItemEvents, FilterDialog.FilterEvent {
    private lateinit var binding: Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //action bar
        binding.toolbarManual.title = intent.getStringExtra("title")
        setSupportActionBar(binding.toolbarManual)

        //options menu listener
        binding.toolbarManual.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filter_lessons -> {
                    val filterDialog = FilterDialog(this)
                    filterDialog.show(supportFragmentManager, "filter_fragment")
                    true
                }

                else -> false
            }
        }

        //data for recyclerView
        val isManual = intent.getBooleanExtra("isManual", true)
        val isPassed = intent.getBooleanExtra("isPassed", true)
        val dataList = lessonsDao.getAllLessons()
        Log.v("testLog", dataList.toString())

        // check booleans and show the related list

        //recyclerView for lessons
        UtilityClass.showRecyclerData(binding.recyclerManual, ArrayList(dataList), this, this)
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
        lessonInfoDialogBinding.infoDialogLessonType.text = when (lesson.lessonTypeId) {
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
//        if (lesson.listOfPrerequisites == null) {
//            lessonInfoDialogBinding.infoDialogPrerequisites.text = "ندارد"
//        } else {
//            lessonInfoDialogBinding.infoDialogPrerequisites.text =
//                lesson.listOfPrerequisites.toString() //extract from database (don't forget forEach)
//        }
//        if (lesson.listOfCorequisites == null) {
//            lessonInfoDialogBinding.infoDialogCorequisites.text = "ندارد"
//        } else {
//            lessonInfoDialogBinding.infoDialogCorequisites.text =
//                lesson.listOfCorequisites.toString() // extract from database
//        }

        //create & show dialog
        val lessonInfoDialog = AlertDialog.Builder(this)
        lessonInfoDialog.setView(lessonInfoDialogBinding.root)
        lessonInfoDialog.create().show()
    }

    override fun onOptionsIconClicked(item: View, popupMenu: android.widget.PopupMenu) {
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.remove_lesson_from_list -> {
                    //remove the lesson from list
                    true
                }

                else -> false
            }
        }
    }

    override fun sendFilterData(lessonTypeId: Int, unitTypeId: Int) {
        //filter the data using these arguments
    }
}