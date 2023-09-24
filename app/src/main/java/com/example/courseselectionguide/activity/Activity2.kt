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
import com.example.courseselectionguide.data.databases.MainDatabase
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.databinding.Activity2Binding
import com.example.courseselectionguide.databinding.DialogLessonDetailBinding
import com.example.courseselectionguide.fragments.FilterDialog

class Activity2 : AppCompatActivity(), AdapterLessons.ItemEvents, FilterDialog.FilterEvent {
    private lateinit var binding: Activity2Binding
    private lateinit var dataList: ArrayList<Lessons>
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
        lessonsDao = MainDatabase.getDatabase(this).lessonsDao

        // check booleans and show the related list
        dataList = if (isManual) {
            ArrayList(lessonsDao.getRemainedLessons())
        } else if (isPassed) {
            ArrayList(lessonsDao.getPassedLessons())
        } else {
            ArrayList(lessonsDao.getFailedLessons())
        }

        //recyclerView for lessons
        UtilityClass.showRecyclerData(binding.recyclerManual, dataList, this, this)
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
        if (lesson.isTheoretical) {
            lessonInfoDialogBinding.infoDialogTheoreticalUnits.text =
                lesson.unitNumber.toString()
            lessonInfoDialogBinding.infoDialogPracticalUnits.text = "0"
        } else {
            lessonInfoDialogBinding.infoDialogTheoreticalUnits.text = "0"
            lessonInfoDialogBinding.infoDialogPracticalUnits.text =
                lesson.unitNumber.toString()
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