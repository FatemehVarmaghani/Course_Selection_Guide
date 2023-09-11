package com.example.courseselectionguide.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.databinding.DialogFilterBinding

class FilterDialog(private val filterEvent: FilterEvent) : DialogFragment() {

    private lateinit var binding: DialogFilterBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(context)
        binding = DialogFilterBinding.inflate(layoutInflater, null, false)
        dialog.setView(binding.root)

        //list for auto completes
        val lessonTypeList =
            arrayListOf("عمومی اسلامی", "عمومی", "پایه", "اصلی", "تخصصی", "تمرکز تخصصی", "اختیاری")
        val lessonFilterAdapter = ArrayAdapter(requireContext(), R.layout.item_text_auto_lesson_type, lessonTypeList)
        (binding.dialogFilterTilLessonType.editText as AutoCompleteTextView).setAdapter(lessonFilterAdapter)
        val unitTypeList =
            arrayListOf("نظری", "عملی")
        val unitFilterAdapter = ArrayAdapter(requireContext(), R.layout.item_text_auto_lesson_type, unitTypeList)
        (binding.dialogFilterTilUnitType.editText as AutoCompleteTextView).setAdapter(unitFilterAdapter)

        //confirm filter
        binding.btnConfirmFilter.setOnClickListener {
            Toast.makeText(context, "dialog fragment reached", Toast.LENGTH_SHORT).show()
            val lessonTypeId = 1
            val unitType = 2

            //send data to activity2
            dismiss()
            filterEvent.sendFilterData(lessonTypeId, unitType)
        }

        return dialog.create()
    }

    interface FilterEvent {
        fun sendFilterData(lessonTypeId: Int, unitTypeId: Int)
    }

}