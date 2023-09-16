package com.example.courseselectionguide.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.Activity2
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.data.CorequisitesList
import com.example.courseselectionguide.data.Lessons
import com.example.courseselectionguide.data.PrerequisitesList
import com.example.courseselectionguide.databinding.DialogLessonDetailBinding
import com.example.courseselectionguide.databinding.FragmentSelectedBinding

class SelectedLessonsFragment : Fragment(), AdapterLessons.ItemEvents {
    private lateinit var binding: FragmentSelectedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //inflating menu
        binding.toolbarSelected.inflateMenu(R.menu.menu_selected_fragment)

        //menu listener
        binding.toolbarSelected.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.add_new_lesson -> {
                    val intent = Intent(context, Activity2::class.java)
                    startActivity(intent)
                    //send data to show remained lessons
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
        val dataList = arrayListOf(
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

        //show lessons on RecyclerView
        val lessonsAdapter = AdapterLessons(requireContext(), dataList, this)
        binding.recyclerSelected.adapter = lessonsAdapter
        binding.recyclerSelected.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

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
        if (lesson.listOfPrerequisites == null) {
            lessonInfoDialogBinding.infoDialogPrerequisites.text = "ندارد"
        } else {
            lessonInfoDialogBinding.infoDialogPrerequisites.text =
                lesson.listOfPrerequisites.toString() //extract from database (don't forget forEach)
        }
        if (lesson.listOfCorequisites == null) {
            lessonInfoDialogBinding.infoDialogCorequisites.text = "ندارد"
        } else {
            lessonInfoDialogBinding.infoDialogCorequisites.text =
                lesson.listOfCorequisites.toString() // extract from database
        }

        //create & show dialog
        val lessonInfoDialog = AlertDialog.Builder(requireContext())
        lessonInfoDialog.setView(lessonInfoDialogBinding.root)
        lessonInfoDialog.create().show()
    }

    override fun onOptionsIconClicked(item: View, popupMenu: PopupMenu) {}
}