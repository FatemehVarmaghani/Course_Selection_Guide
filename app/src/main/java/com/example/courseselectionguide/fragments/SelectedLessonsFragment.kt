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
import com.example.courseselectionguide.classes.UtilityClass
import com.example.courseselectionguide.data.data_classes.CorequisitesList
import com.example.courseselectionguide.data.data_classes.Lessons
import com.example.courseselectionguide.data.data_classes.PrerequisitesList
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
                    intent.putExtra("title", getString(R.string.manual_selection))
                    intent.putExtra("isManual", true)
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
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "فیزیک 1",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "فارسی عمومی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 2,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "زبان عمومی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 2,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "مبانی کامپیوتر و برنامه سازی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "اندیش اسلامی 1",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 1,
                unitType = true,
                recommendedSemester = 1,
                lessonOrientationId = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "تاریخ فرهنگ و تمدن اسلام و ایران",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 2,
                unitType = true,
                recommendedSemester = 1,
                lessonState = 1
            ),
            Lessons(
                lessonName = "فیزیک 2",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(2)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "ریاضی عمومی 2",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(1)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "ریاضیات گسسته",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 2,
                listOfCorequisites = CorequisitesList(arrayListOf(1, 5)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "معادلات دیفرانسیل",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 3,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(1)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "اندیشه اسلامی 2",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 1,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(6)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "برنامه سازی پیشرفته",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(5)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "کارگاه کامپیوتر",
                theoreticalUnitNumber = 0f,
                practicalUnitNumber = 1f,
                lessonTypeId = 3,
                unitType = false,
                recommendedSemester = 2,
                listOfPrerequisites = PrerequisitesList(arrayListOf(5)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "تربیت بدنی",
                theoreticalUnitNumber = 0.5f,
                practicalUnitNumber = 0.5f,
                lessonTypeId = 2,
                recommendedSemester = 2,
                lessonState = 1
            ),
            Lessons(
                lessonName = "مدارهای الکتریکی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(11)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "آزمایشگاه فیزیک 2",
                theoreticalUnitNumber = 0f,
                practicalUnitNumber = 1f,
                lessonTypeId = 3,
                unitType = false,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(8)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "آمار و احتمال مهندسی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(9)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "ساختمان داده",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(10, 13)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "مدارهای منطقی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfCorequisites = CorequisitesList(arrayListOf(10)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "ریاضیات مهندسی",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(9, 11)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "انقلاب اسلامی",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 1,
                unitType = true,
                recommendedSemester = 3,
                lessonState = 1
            ),
            Lessons(
                lessonName = "زبان تخصصی",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 3,
                listOfPrerequisites = PrerequisitesList(arrayListOf(4)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "نظریه زبان ها و ماشین  ها",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(19)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "طراحی الگوریتم",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(19)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "سیگنال ها و سیستم ها",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(21)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "معماری کامپیوتر",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(20)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "پایگاه داده",
                theoreticalUnitNumber = 3f,
                practicalUnitNumber = 0f,
                lessonTypeId = 5,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(19)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "روش پژوهش و ارائه",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 4,
                unitType = true,
                recommendedSemester = 4,
                listOfPrerequisites = PrerequisitesList(arrayListOf(23)),
                lessonState = 1
            ),
            Lessons(
                lessonName = "آئین زندگی (اخلاق کاربردی)",
                theoreticalUnitNumber = 2f,
                practicalUnitNumber = 0f,
                lessonTypeId = 1,
                unitType = true,
                recommendedSemester = 4,
                lessonState = 1
            )
        )

        //show lessons on RecyclerView
        UtilityClass.showRecyclerData(binding.recyclerSelected, dataList, requireContext(), this)

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