package com.example.courseselectionguide.activity

import android.content.Intent
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import com.example.courseselectionguide.R
import com.example.courseselectionguide.databinding.ActivityDataEntryBinding

class DataEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //views
        binding = ActivityDataEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sharedPref's editor
        val editor = sharedPref.edit()

        //variables
        var currentSemester: Int
        var averageText: String?
        var average: Float?
        var isSenior: Boolean? = null
        var hasFailed: Boolean? = null

        //assign value to variables by income data except average
        currentSemester = numberPicker().value
        numberPicker().setOnValueChangedListener { _, _, newVal ->
            currentSemester = newVal
        }
        binding.radioGroupFailedLesson.setOnCheckedChangeListener { _, checkedId ->
            val hasFailedValue = when (checkedId) {
                R.id.radio_failed_lesson_yes -> true
                R.id.radio_failed_lesson_no -> false
                else -> null
            }
            hasFailed = hasFailedValue
        }
        binding.radioGroupLastSemester.setOnCheckedChangeListener { _, checkedId ->
            val isSeniorValue = when (checkedId) {
                R.id.radio_last_semester_yes -> true
                R.id.radio_last_semester_no -> false
                else -> null
            }
            isSenior = isSeniorValue
        }

        //click on button
        binding.btnRegistration.setOnClickListener {
            averageText = binding.tilAverage.editText?.text.toString()
            if (!averageText.isNullOrEmpty() && currentSemester == 1) {
                Toast.makeText(this, "اگر ترم یک هستید معدل را وارد نکنید!", Toast.LENGTH_SHORT)
                    .show()
            } else if (hasFailed == true && currentSemester == 1) {
                Toast.makeText(this, "اگر ترم یک هستید درس رد/حذف شده ندارید!", Toast.LENGTH_SHORT)
                    .show()
            } else if (isSenior == true && currentSemester == 1) {
                Toast.makeText(this, "ترم یک هستید یا ترم آخر؟!", Toast.LENGTH_SHORT).show()
            } else if (currentSemester == 1) {
                averageText = "20"
                try {
                    average = averageText!!.toFloat()
                    //pass data to sharedPref
                    passDataToSharedPref(currentSemester, average!!, hasFailed!!, isSenior!!, editor)
                    //going to main activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "معدل خود را درست وارد کنید!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (!averageText.isNullOrEmpty() && hasFailed != null && isSenior != null) {
                try {
                    average = averageText!!.toFloat()
                    if (average!! in 0.0..20.0) {
                        //pass data to sharedPref
                        passDataToSharedPref(currentSemester, average!!, hasFailed!!, isSenior!!, editor)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "معدل باید بین 0 و 20 باشد!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "معدل خود را درست وارد کنید!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "همه اطلاعات را وارد کنید!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun passDataToSharedPref(
        currentSemester: Int,
        average: Float,
        hasFailed: Boolean,
        isSenior: Boolean,
        editor: Editor
    ) {
        editor.putInt(CURRENT_SEMESTER, currentSemester)
        editor.putFloat(AVERAGE, average)
        editor.putBoolean(HAS_FAILED, hasFailed)
        editor.putBoolean(IS_SENIOR, isSenior)
        editor.putInt(
            USER_STATE,
            userState(average, hasFailed, isSenior, currentSemester)
        )
        editor.putBoolean(FIRST_RUNNING, false)
        editor.apply()
    }

    private fun numberPicker(): NumberPicker {
        val numberPicker = binding.numberPickerCurrentSemester
        numberPicker.minValue = 1
        numberPicker.maxValue = 12
        return numberPicker
    }

    companion object {
        fun userState(
            average: Float,
            failedLesson: Boolean,
            lastSemester: Boolean,
            currentSemester: Int
        ): Int {
            return if (lastSemester) {
                3
            } else if (currentSemester == 1) {
                2
            } else if (average >= 17 && !failedLesson) {
                1
            } else if (average >= 12){
                2
            } else {
                4
            }
        }
    }
}