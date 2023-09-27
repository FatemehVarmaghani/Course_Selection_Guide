package com.example.courseselectionguide.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.AVERAGE
import com.example.courseselectionguide.activity.CURRENT_SEMESTER
import com.example.courseselectionguide.activity.HAS_FAILED
import com.example.courseselectionguide.activity.IS_SENIOR
import com.example.courseselectionguide.databinding.DialogEditUserInfoBinding

class EditDialog(private val editInfoEvent: EditInfoEvent) : DialogFragment() {

    private lateinit var binding: DialogEditUserInfoBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editDialog = AlertDialog.Builder(context)
        binding = DialogEditUserInfoBinding.inflate(layoutInflater, null, false)
        editDialog.setView(binding.root)

        //create dialog
        val createdEditDialog: AlertDialog = editDialog.create()

        //show data on dialog
        binding.edtTxtCurrentSemester.setText(requireArguments().getInt(CURRENT_SEMESTER).toString())
        if (requireArguments().getInt(CURRENT_SEMESTER) != 1) {
            binding.edtTxtAverage.setText(requireArguments().getFloat(AVERAGE).toString())
        }
        if (requireArguments().getBoolean(HAS_FAILED))
            binding.dialogRFailedLessonYes.isChecked = true
        else
            binding.dialogRFailedLessonNo.isChecked = true
        if (requireArguments().getBoolean(IS_SENIOR))
            binding.dialogRLastSemesterYes.isChecked = true
        else
            binding.dialogRLastSemesterNo.isChecked = true

        //variables & getting new user info from user
        var newAverage = requireArguments().getFloat(AVERAGE)
        var newIsSenior = requireArguments().getBoolean(IS_SENIOR)
        var newHasFailed = requireArguments().getBoolean(HAS_FAILED)
        var newCurrentSemester = binding.dialogTilCurrentSemester.editText!!.text.toString().toInt()
        var newAverageText = ""
        binding.dialogRgFailedLesson.setOnCheckedChangeListener { _, checkedId ->
            val hasFailedValue = when (checkedId) {
                R.id.dialog_r_failed_lesson_yes -> true
                R.id.dialog_r_failed_lesson_no -> false
                else -> null
            }
            newHasFailed = hasFailedValue!!
        }
        binding.dialogRgLastSemester.setOnCheckedChangeListener { _, checkedId ->
            val isSeniorValue = when (checkedId) {
                R.id.dialog_r_last_semester_yes -> true
                R.id.dialog_r_last_semester_no -> false
                else -> null
            }
            newIsSenior = isSeniorValue!!
        }

        //checking new info on click
        binding.btnEditInfo.setOnClickListener {
            newAverageText = binding.dialogTilAverage.editText?.text.toString()
            newCurrentSemester = binding.dialogTilCurrentSemester.editText!!.text.toString().toInt()
            if (newAverageText.isNotEmpty() && newCurrentSemester == 1) {
                Toast.makeText(requireContext(), "اگر ترم یک هستید معدل را وارد نکنید!", Toast.LENGTH_SHORT)
                    .show()
            } else if (newHasFailed && newCurrentSemester == 1) {
                Toast.makeText(requireContext(), "اگر ترم یک هستید درس رد/حذف شده ندارید!", Toast.LENGTH_SHORT)
                    .show()
            } else if (newIsSenior && newCurrentSemester == 1) {
                Toast.makeText(requireContext(), "ترم یک هستید یا ترم آخر؟!", Toast.LENGTH_SHORT).show()
            } else if (newCurrentSemester == 1) {
                newAverageText = "20"
                try {
                    newAverage = newAverageText.toFloat()
                    editInfoEvent.sendUserInfo(newCurrentSemester, newAverage, newHasFailed, newIsSenior)
                    //dismiss the dialog if possible
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "معدل خود را درست وارد کنید!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (newAverageText.isNotEmpty()) {
                try {
                    newAverage = newAverageText.toFloat()
                    if (newAverage in 0.0..20.0) {
                        //send new data back to state fragment
                        editInfoEvent.sendUserInfo(newCurrentSemester, newAverage, newHasFailed, newIsSenior)
                        //dismiss the dialog if possible
                    } else {
                        Toast.makeText(requireContext(), "معدل باید بین 0 و 20 باشد!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), "معدل خود را درست وارد کنید!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "همه اطلاعات را وارد کنید!", Toast.LENGTH_SHORT).show()
            }
        }

        return createdEditDialog
    }

    interface EditInfoEvent {
        fun sendUserInfo(
            currentSemester: Int,
            average: Float,
            hasFailed: Boolean,
            isSenior: Boolean
        )
    }

}