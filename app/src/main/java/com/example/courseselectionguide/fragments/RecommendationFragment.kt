package com.example.courseselectionguide.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                    Toast.makeText(
                        requireContext(),
                        "add recommended list to selected",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }

                R.id.show_new_recommended_list -> {
                    Toast.makeText(
                        requireContext(),
                        "show new recommended list",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }

                else -> false
            }
        }

        //data for textViews
        sharedPref = requireContext().getSharedPreferences(PRIMITIVE_DATA, Context.MODE_PRIVATE)
        binding.txtRecommendedCurrentSemester.text = getCurrentSemester()
        binding.txtRecommendationUserState.text = userStateDao.getStateName(
            sharedPref.getInt(
                USER_STATE, 2
            )
        )

        //list of selected lessons
        dataList = ArrayList(lessonsDao.getRecommendedLessons(getSemester()))

        //show lessons on recyclerView
        UtilityClass.showRecyclerData(
            binding.recyclerRecommendation,
            dataList,
            requireContext(),
            this,
            R.menu.menu_item_recommended_lesson
        )
    }

    private fun getSemester(): Int {
        return when (val currentSemester = sharedPref.getInt(CURRENT_SEMESTER, 1)) {
            1 - 7 -> currentSemester
            8 - 12 -> 8
            else -> 1
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
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
        val corequisites = corequisitesDao.getCorequisites(lesson.lessonId)
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
                    UtilityClass.addLessonToSelected(lesson, requireContext())
                    goToMainActivity()
                    true
                }

                R.id.add_recommended_to_passed -> {
                    UtilityClass.addLessonToPassed(lesson, requireContext())
                    goToMainActivity()
                    true
                }

                R.id.add_recommended_to_failed -> {
                    UtilityClass.addLessonToFailed(lesson, requireContext())
                    goToMainActivity()
                    true
                }

                else -> false
            }
        }
    }

}