package com.example.courseselectionguide.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
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
    private var menuId = R.menu.menu_item_manual_select
    private var isManual = false
    private var isPassed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflating layout
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
        isManual = intent.getBooleanExtra("isManual", true)
        isPassed = intent.getBooleanExtra("isPassed", true)
        lessonsDao = MainDatabase.getDatabase(this).lessonsDao

        // check booleans: show the related list and item menu
        if (isManual) {
            dataList = ArrayList(lessonsDao.getRemainedLessons())
            menuId = R.menu.menu_item_manual_select
        } else if (isPassed) {
            dataList = ArrayList(lessonsDao.getPassedLessons())
            menuId = R.menu.menu_item_passed_lesson
        } else {
            dataList = ArrayList(lessonsDao.getFailedLessons())
            menuId = R.menu.menu_item_failed_lesson
        }
        UtilityClass.showRecyclerData(binding.recyclerManual, dataList, this, this, menuId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_activity2, menu)

        //get searchView items
        val searchItem = menu?.findItem(R.id.search_lesson)
        val searchView = searchItem?.actionView as SearchView

        //action for query
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchOnDataBase(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchOnDataBase(newText)
                return true
            }
        })

        return true
    }

    private fun searchOnDataBase(searchText: String?) {
        if (!searchText.isNullOrEmpty()) {
            val searchedList: List<Lessons> = if (isManual) {
                lessonsDao.searchOnRemained(searchText)
            } else if (isPassed) {
                lessonsDao.searchOnPassed(searchText)
            } else {
                lessonsDao.searchOnFailed(searchText)
            }
            UtilityClass.showRecyclerData(
                binding.recyclerManual,
                ArrayList(searchedList),
                this,
                this,
                menuId
            )
        } else {
            UtilityClass.showRecyclerData(binding.recyclerManual, dataList, this, this, menuId)
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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

    override fun onOptionsIconClicked(item: View, popupMenu: android.widget.PopupMenu, lesson: Lessons) {
        popupMenu.setOnMenuItemClickListener {
            if (isManual) {
                when (it.itemId) {
                    R.id.add_manual_to_selected -> {
                        UtilityClass.addLessonToSelected(lesson, this)
                        goToMainActivity()
                        true
                    }

                    R.id.add_manual_to_passed -> {
                        UtilityClass.addLessonToPassed(lesson, this)
                        goToMainActivity()
                        true
                    }

                    R.id.add_manual_to_failed -> {
                        UtilityClass.addLessonToFailed(lesson, this)
                        goToMainActivity()
                        true
                    }

                    else -> false
                }
            } else if (isPassed) {
                when (it.itemId) {
                    R.id.remove_from_passed -> {
                        UtilityClass.addLessonToRemained(lesson, this)
                        goToMainActivity()
                        true
                    }

                    else -> false
                }
            } else {
                when (it.itemId) {
                    R.id.remove_from_failed -> {
                        UtilityClass.addLessonToRemained(lesson, this)
                        goToMainActivity()
                        true
                    }

                    else -> false
                }
            }
            //go to fragments or reload activity
        }
    }

    override fun sendFilterData(lessonTypeId: Int, isTheoretical: Boolean) {
        val filteredData = if (isManual) {
            lessonsDao.filterRemained(lessonTypeId, isTheoretical)
        } else if (isPassed) {
            lessonsDao.filterPassed(lessonTypeId, isTheoretical)
        } else {
            lessonsDao.filterFailed(lessonTypeId, isTheoretical)
        }
        UtilityClass.showRecyclerData(binding.recyclerManual, ArrayList(filteredData), this, this, menuId)
    }
}