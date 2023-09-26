package com.example.courseselectionguide.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.Activity2
import com.example.courseselectionguide.activity.MainActivity
import com.example.courseselectionguide.activity.corequisitesDao
import com.example.courseselectionguide.activity.lessonsDao
import com.example.courseselectionguide.activity.prerequisitesDao
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.classes.UtilityClass
import com.example.courseselectionguide.data.databases.MainDatabase
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.databinding.DialogLessonDetailBinding
import com.example.courseselectionguide.databinding.FragmentSelectedBinding

class SelectedLessonsFragment : Fragment(), AdapterLessons.ItemEvents {
    private lateinit var binding: FragmentSelectedBinding
    private lateinit var dataList: ArrayList<Lessons>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //dao
        lessonsDao = MainDatabase.getDatabase(requireContext()).lessonsDao

        //inflating menu
        binding.toolbarSelected.inflateMenu(R.menu.menu_selected_fragment)

        //menu listener
        binding.toolbarSelected.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.add_new_lesson -> {
                    goToActivity2()
                    true
                }
                R.id.clear_selected_list -> {
                    //all of the selected list will be deleted
                    true
                }
                else -> false
            }
        }

        //list of selected lessons
        dataList = ArrayList(lessonsDao.getSelectedLessons())

        //show lessons on RecyclerView
        UtilityClass.showRecyclerData(binding.recyclerSelected, dataList, requireContext(), this, R.menu.menu_item_selected_lesson)

    }

    private fun goToActivity2() {
        val intent = Intent(context, Activity2::class.java)
        intent.putExtra("title", getString(R.string.manual_selection))
        intent.putExtra("isManual", true)
        startActivity(intent)
    }

    private fun goToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
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
            lessonInfoDialogBinding.infoDialogCorequisites.text = preString
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
                R.id.remove_from_selected -> {
                    UtilityClass.addLessonToRemained(lesson, requireContext())
                    goToMainActivity()
                    true
                }
                R.id.add_selected_to_passed -> {
                    UtilityClass.addLessonToPassed(lesson, requireContext())
                    goToMainActivity()
                    true
                }
                else -> false
            }
        }
    }
}