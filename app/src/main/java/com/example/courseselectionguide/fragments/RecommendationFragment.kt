package com.example.courseselectionguide.fragments

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
import com.example.courseselectionguide.activity.lessonsDao
import com.example.courseselectionguide.activity.sharedPref
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

        //inflating menu
        binding.toolbarRecommended.inflateMenu(R.menu.menu_recommended_fragment)

        //menu listener
        binding.toolbarRecommended.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_recommended_list_to_selected -> {
                    Toast.makeText(requireContext(), "add recommended list to selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.show_new_recommended_list -> {
                    Toast.makeText(requireContext(), "show new recommended list", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

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
        return when(val currentSemester = sharedPref.getInt(CURRENT_SEMESTER, 1)) {
            1 - 7 -> currentSemester
            8 - 12 -> 8
            else -> 1
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
        val lessonInfoDialog = AlertDialog.Builder(requireContext())
        lessonInfoDialog.setView(lessonInfoDialogBinding.root)
        lessonInfoDialog.create().show()
    }

    override fun onOptionsIconClicked(item: View, popupMenu: PopupMenu) {
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_recommended_to_selected -> {
                    Toast.makeText(requireContext(), "add recommended to selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.add_recommended_to_passed -> {
                    Toast.makeText(requireContext(), "add recommended to passed", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

}