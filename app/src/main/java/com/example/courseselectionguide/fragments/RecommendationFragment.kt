package com.example.courseselectionguide.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import com.example.courseselectionguide.R
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.classes.UtilityClass
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.databinding.DialogLessonDetailBinding
import com.example.courseselectionguide.databinding.FragmentRecommendationBinding

class RecommendationFragment : Fragment(), AdapterLessons.ItemEvents {

    private lateinit var binding: FragmentRecommendationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecommendationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //inflating menu
        binding.toolbarRecommended.inflateMenu(R.menu.menu_recommended_fragment)

        //menu listener
        binding.toolbarRecommended.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_recommended_to_selected -> {
                    //add current recommended list to selected and clear recommended list
                    true
                }

                R.id.show_new_recommended_list -> {
                    //clear current recommended list and show a new one
                    true
                }

                else -> false
            }
        }

        //list of selected lessons
        val dataList = arrayListOf(
            Lessons(
                lessonName = "ریاضی عمومی 1",
                unitNumber = 3,
                lessonTypeId = 3,
                isTheoretical = true,
                recommendedSemester = 1,
                lessonState = 1,
                isFixed = true
            ),
            Lessons(
                lessonName = "فیزیک 1",
                unitNumber = 3,
                lessonTypeId = 3,
                isTheoretical = true,
                recommendedSemester = 1,
                lessonState = 1,
                isFixed = true
            ),
            Lessons(
                lessonName = "فارسی عمومی",
                unitNumber = 3,
                lessonTypeId = 2,
                isTheoretical = true,
                recommendedSemester = 1,
                lessonState = 1,
                isFixed = true
            ),
            Lessons(
                lessonName = "زبان عمومی",
                unitNumber = 3,
                lessonTypeId = 2,
                isTheoretical = true,
                recommendedSemester = 1,
                lessonState = 1,
                isFixed = true
            ),
            Lessons(
                lessonName = "مبانی کامپیوتر و برنامه سازی",
                unitNumber = 3,
                lessonTypeId = 4,
                isTheoretical = true,
                recommendedSemester = 1,
                lessonState = 1,
                isFixed = true
            ),
            Lessons(
                lessonName = "اندیش اسلامی 1",
                unitNumber = 2,
                lessonTypeId = 1,
                isTheoretical = true,
                recommendedSemester = 1,
                lessonOrientationId = 1,
                lessonState = 1,
                isFixed = true
            )
        )

        //show lessons on recyclerView
        UtilityClass.showRecyclerData(
            binding.recyclerRecommendation,
            dataList,
            requireContext(),
            this
        )
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

    override fun onOptionsIconClicked(item: View, popupMenu: PopupMenu) {}

}