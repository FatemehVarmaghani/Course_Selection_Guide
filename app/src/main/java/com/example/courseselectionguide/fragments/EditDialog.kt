package com.example.courseselectionguide.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.DataEntryActivity
import com.example.courseselectionguide.activity.MainActivity
import com.example.courseselectionguide.activity.sharedPref
import com.example.courseselectionguide.databinding.DialogEditUserInfoBinding

class EditDialog(private val editInfoEvent: EditInfoEvent) : DialogFragment() {

    private lateinit var binding: DialogEditUserInfoBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editDialog = AlertDialog.Builder(context)
        binding = DialogEditUserInfoBinding.inflate(layoutInflater, null, false)
        editDialog.setView(binding.root)

        //get data from state fragment
        var currentSemester = requireArguments().getInt("current_semester")
        var average = requireArguments().getFloat("average")
        var hasFailed = requireArguments().getBoolean("has_failed")
        var isSenior = requireArguments().getBoolean("is_senior")

        //new data
        var newCurrentSemester: Int?
        var newAverageText: String?
        var newAverage: Float?
        var newIsSenior: Boolean? = null
        var newHasFailed: Boolean? = null

        //shared preference
        val editor = sharedPref.edit()

        //create dialog
        val createdEditDialog: AlertDialog = editDialog.create()

        //show data on dialog
        binding.edtTxtCurrentSemester.setText(currentSemester.toString())
        binding.edtTxtAverage.setText(average.toString())
        if (hasFailed)
            binding.dialogRFailedLessonYes.isChecked = true
        else
            binding.dialogRFailedLessonNo.isChecked = true
        if (isSenior)
            binding.dialogRLastSemesterYes.isChecked = true
        else
            binding.dialogRLastSemesterNo.isChecked = true

        //getting new user info from user
        newCurrentSemester = binding.dialogTilCurrentSemester.editText!!.text.toString().toInt()
        newAverageText = binding.dialogTilAverage.editText!!.text.toString()
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
            if (!newAverageText.isNullOrEmpty() && currentSemester == 1) {
                Toast.makeText(context, "اگر ترم یک هستید معدل را وارد نکنید!", Toast.LENGTH_SHORT)
                    .show()
            } else if (hasFailed && currentSemester == 1) {
                Toast.makeText(
                    context,
                    "اگر ترم یک هستید درس رد/حذف شده ندارید!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (isSenior && currentSemester == 1) {
                Toast.makeText(context, "ترم یک هستید یا ترم آخر؟!", Toast.LENGTH_SHORT).show()
            } else if (currentSemester == 1) {
                newAverageText = "20"

                if (!newAverageText.isNullOrEmpty()) {
                    try {
                        newAverage = newAverageText!!.toFloat()
                        if (newAverage!! in 0.0..20.0) {
                            //pass data to sharedPref
                            editor.putInt("current_semester", newCurrentSemester)
                            editor.putFloat("average", newAverage!!)
                            editor.putBoolean("has_failed", newHasFailed!!)
                            editor.putBoolean("is_senior", newIsSenior!!)
                            editor.putInt(
                                "user_state",
                                DataEntryActivity.userState(
                                    newAverage!!,
                                    newHasFailed!!,
                                    newIsSenior!!,
                                    newCurrentSemester
                                )
                            )
                            editor.apply()

                            //sending new user info to state fragment
                            editInfoEvent.sendUserInfo(
                                newCurrentSemester,
                                newAverage!!, newHasFailed!!, newIsSenior!!
                            )

                            //going to the main activity
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                            dismiss()
                        } else {
                            Toast.makeText(
                                context,
                                "معدل باید بین 0 و 20 باشد!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } catch (e: NumberFormatException) {
                        Toast.makeText(context, "معدل خود را درست وارد کنید!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(context, "همه اطلاعات را وارد کنید!", Toast.LENGTH_SHORT).show()
                }
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