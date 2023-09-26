package com.example.courseselectionguide.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.CURRENT_SEMESTER
import com.example.courseselectionguide.activity.MainActivity
import com.example.courseselectionguide.activity.PRIMITIVE_DATA
import com.example.courseselectionguide.activity.USER_STATE
import com.example.courseselectionguide.activity.corequisitesDao
import com.example.courseselectionguide.activity.lessonsDao
import com.example.courseselectionguide.activity.prerequisitesDao
import com.example.courseselectionguide.activity.sharedPref
import com.example.courseselectionguide.activity.userStateDao
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.classes.UtilityClass
import com.example.courseselectionguide.data.databases.MainDatabase
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.databinding.DialogLessonDetailBinding
import com.example.courseselectionguide.databinding.FragmentRecommendationBinding

class RecommendationFragment : Fragment(), AdapterLessons.ItemEvents {
    private lateinit var binding: FragmentRecommendationBinding
    private lateinit var dataList: ArrayList<Lessons>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecommendationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //dao
        lessonsDao = MainDatabase.getDatabase(requireContext()).lessonsDao
        userStateDao = MainDatabase.getDatabase(requireContext()).userStateDao

        //inflating menu
        binding.toolbarRecommended.inflateMenu(R.menu.menu_recommended_fragment)

        //menu listener
        binding.toolbarRecommended.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_recommended_list_to_selected -> {
                    for (eachLesson in dataList) {
                        UtilityClass.addLessonToSelected(eachLesson, requireContext())
                    }
                    loadRecommendedList(dataList[0].recommendedSemester)
                    true
                }

                R.id.add_recommended_list_to_passed -> {
                    for (eachLesson in dataList) {
                        UtilityClass.addLessonToPassed(eachLesson, requireContext())
                    }
                    loadRecommendedList(dataList[0].recommendedSemester)
                    true
                }

                else -> false
            }
        }


        //data for textViews
        sharedPref = requireContext().getSharedPreferences(PRIMITIVE_DATA, Context.MODE_PRIVATE)
        numberPicker().setOnValueChangedListener { _, _, newVal ->
            loadRecommendedList(getSemester(newVal))
        }

        //list of selected lessons
        loadRecommendedList(getSemester(numberPicker().value))
    }

    private fun loadRecommendedList(semester: Int) {
        dataList = ArrayList(lessonsDao.getRecommendedLessons(semester))
        //show lessons on recyclerView
        UtilityClass.showRecyclerData(
            binding.recyclerRecommendation,
            dataList,
            requireContext(),
            this,
            R.menu.menu_item_recommended_lesson
        )
    }

    private fun getSemester(currentSemester: Int): Int {
        return when (currentSemester) {
            in 1..7 -> currentSemester
            in 8 .. 12 -> 8
            else -> -1
        }
    }

    private fun numberPicker(): NumberPicker {
        val numberPicker = binding.numberPickerRecommendedSemester
        numberPicker.minValue = 1
        numberPicker.maxValue = 12
        numberPicker.value = sharedPref.getInt(CURRENT_SEMESTER, 1)
        return numberPicker
    }

    private fun getCurrentSemester(): String {
        return when (sharedPref.getInt(CURRENT_SEMESTER, 1)) {
            1 -> "یک"
            2 -> "دو"
            3 -> "سه"
            4 -> "چهار"
            5 -> "پنج"
            6 -> "شش"
            7 -> "هفت"
            8 -> "هشت"
            9 -> "نه"
            10 -> "ده"
            11 -> "یازده"
            12 -> "دوازده"
            else -> ""
        }
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
                lesson.unitNumber.toInt().toString()
        }

        //pre and co =
        lessonsDao = MainDatabase.getDatabase(requireContext()).lessonsDao
        prerequisitesDao = MainDatabase.getDatabase(requireContext()).prerequisitesDao
        corequisitesDao = MainDatabase.getDatabase(requireContext()).corequisitesDao
        val prerequisites = prerequisitesDao.getPrerequisitesByMainLesson(lesson.lessonId!!)
        val corequisites = corequisitesDao.getCorequisitesByMainLesson(lesson.lessonId)
        var preString = ""
        var coString = ""
        if (prerequisites.isNotEmpty()) {
            for (preRel in prerequisites) {
                preString += lessonsDao.getLesson(preRel.prerequisiteLessonId).lessonName
                preString += " "
            }
            lessonInfoDialogBinding.infoDialogPrerequisites.text = preString
        } else {
            lessonInfoDialogBinding.infoDialogPrerequisites.text = "ندارد"
        }
        if (corequisites.isNotEmpty()) {
            for (coRel in corequisites) {
                coString += lessonsDao.getLesson(coRel.corequisiteLessonId).lessonName
                coString += " "
            }
            lessonInfoDialogBinding.infoDialogCorequisites.text = coString
        } else {
            lessonInfoDialogBinding.infoDialogCorequisites.text = "ندارد"
        }

        //create & show dialog
        val lessonInfoDialog = AlertDialog.Builder(requireContext())
        lessonInfoDialog.setView(lessonInfoDialogBinding.root)
        lessonInfoDialog.create().show()
    }

    override fun onOptionsIconClicked(item: View, popupMenu: PopupMenu, lesson: Lessons) {
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_recommended_to_selected -> {
                    //change lessonState from remained or failed (recommended)  to passed
                    UtilityClass.addLessonToSelected(lesson, requireContext())
                    true
                }

                R.id.add_recommended_to_passed -> {
                    //change lessonState from remained or failed (recommended) to passed
                    UtilityClass.addLessonToPassed(lesson, requireContext())
                    true
                }

                R.id.add_recommended_to_failed -> {
                    //change lesson state from remained or failed (recommended) to passed
                    UtilityClass.addLessonToFailed(lesson, requireContext())
                    true
                }

                else -> false
            }
        }
    }

    companion object {
        fun getMaxUnitNumber(context: Context): Int {
            sharedPref = context.getSharedPreferences(PRIMITIVE_DATA, Context.MODE_PRIVATE)
            return when (sharedPref.getInt(USER_STATE, 1)) {
                1 -> 24
                2 -> 20
                3 -> 25
                4 -> 14
                else -> 20
            }
        }
    }

}