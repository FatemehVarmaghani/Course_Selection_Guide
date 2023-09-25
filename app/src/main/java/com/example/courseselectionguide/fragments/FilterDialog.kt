package com.example.courseselectionguide.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
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

        //inflating and setting view
        binding = DialogFilterBinding.inflate(layoutInflater, null, false)
        dialog.setView(binding.root)

        //list for auto completes
        val lessonTypeList =
            arrayListOf("عمومی اسلامی", "عمومی", "پایه", "اصلی", "تخصصی", "تمرکز تخصصی", "اختیاری")
        val lessonFilterAdapter =
            ArrayAdapter(requireContext(), R.layout.item_text_auto_lesson_type, lessonTypeList)
        val lessonTypeAutoCompleteTextView =
            binding.dialogFilterTilLessonType.editText as AutoCompleteTextView
        lessonTypeAutoCompleteTextView.setAdapter(lessonFilterAdapter)

        val unitTypeList =
            arrayListOf("نظری", "عملی")
        val unitFilterAdapter =
            ArrayAdapter(requireContext(), R.layout.item_text_auto_lesson_type, unitTypeList)
        val unitTypeAutoCompleteTextView =
            binding.dialogFilterTilUnitType.editText as AutoCompleteTextView
        unitTypeAutoCompleteTextView.setAdapter(unitFilterAdapter)

        //confirm filter
        binding.btnConfirmFilter.setOnClickListener {
            //get selected values
            val selectedLessonTypeId = lessonTypeAutoCompleteTextView.text.toString()
            val selectedIsTheo = unitTypeAutoCompleteTextView.text.toString()
            Log.v("lessonType", selectedLessonTypeId)
            Log.v("unitType", selectedIsTheo)
            //map the values
            val lessonTypeId = getTypeId(selectedLessonTypeId)
            val isTheoretical = getIsTheo(selectedIsTheo)

            if (selectedLessonTypeId.isEmpty() || selectedIsTheo.isEmpty()) {
                Toast.makeText(requireContext(), "هر دو مورد را انتخاب کنید!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Send data to activity2 & dismiss dialog
                filterEvent.sendFilterData(lessonTypeId, isTheoretical)
                dismiss()
            }
        }

        return dialog.create()
    }

    private fun getTypeId(lessonType: String): Int {
        return when (lessonType) {
            "عمومی اسلامی" -> 1
            "عمومی" -> 2
            "پایه" -> 3
            "اصلی" -> 4
            "تخصصی" -> 5
            "تمرکز تخصصی" -> 6
            "اختیاری" -> 7
            else -> 1
        }
    }

    private fun getIsTheo(unitType: String): Boolean {
        return when (unitType) {
            "نظری" -> true
            "عملی" -> false
            else -> true
        }
    }

    interface FilterEvent {
        fun sendFilterData(lessonTypeId: Int, isTheoretical: Boolean)
    }
}
