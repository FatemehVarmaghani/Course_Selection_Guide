package com.example.courseselectionguide.activity

import android.content.Intent
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
            if (!averageText.isNullOrEmpty() && hasFailed != null && isSenior != null) {
                try {
                    average = averageText!!.toFloat()
                    if (average!! in 0.0..20.0) {
                        //pass data to sharedPref
                        editor.putInt("current_semester", currentSemester)
                        editor.putFloat("average", average!!)
                        editor.putBoolean("has_failed", hasFailed!!)
                        editor.putBoolean("is_senior", isSenior!!)
                        editor.putBoolean("data_entry_shown", true)
                        editor.apply()
                        //going to the main activity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "معدل باید بین 0 و 20 باشد!", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "معدل خود را درست وارد کنید!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "همه اطلاعات را وارد کنید!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun numberPicker(): NumberPicker {
        val numberPicker = binding.numberPickerCurrentSemester
        numberPicker.minValue = 1
        numberPicker.maxValue = 12
        return numberPicker
    }
}