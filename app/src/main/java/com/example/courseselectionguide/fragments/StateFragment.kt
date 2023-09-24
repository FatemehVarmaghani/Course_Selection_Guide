package com.example.courseselectionguide.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.AVERAGE
import com.example.courseselectionguide.activity.Activity2
import com.example.courseselectionguide.activity.CURRENT_SEMESTER
import com.example.courseselectionguide.activity.HAS_FAILED
import com.example.courseselectionguide.activity.IS_SENIOR
import com.example.courseselectionguide.activity.PRIMITIVE_DATA
import com.example.courseselectionguide.activity.USER_STATE
import com.example.courseselectionguide.activity.lessonsDao
import com.example.courseselectionguide.activity.sharedPref
import com.example.courseselectionguide.activity.userStateDao
import com.example.courseselectionguide.data.databases.MainDatabase
import com.example.courseselectionguide.databinding.FragmentStateBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class StateFragment : Fragment(), EditDialog.EditInfoEvent {
    private lateinit var binding: FragmentStateBinding
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //dao
        lessonsDao = MainDatabase.getDatabase(requireContext()).lessonsDao
        userStateDao = MainDatabase.getDatabase(requireContext()).userStateDao

        //variables for user info
        sharedPref = requireContext().getSharedPreferences(PRIMITIVE_DATA, Context.MODE_PRIVATE)
        val passedUnitsNumber = countPassedUnits()
        val failedUnitsNumber = countFailedUnits()
        val remainedUnitsNumber = 144 - passedUnitsNumber

        //show user info on textViews
        binding.txtStateRemainedUnits.text = remainedUnitsNumber.toString()
        binding.txtStateFailedUnits.text = failedUnitsNumber.toString()
        binding.txtStatePassedUnits.text = passedUnitsNumber.toString()
        binding.txtStateCurrentSemester.text = getCurrentSemester()
        binding.txtStateStudentState.text = getUserState()

        //pie chart
        pieChart = binding.pieChartState
        val pieEntries = arrayListOf(
            PieEntry(passedUnitsNumber.toFloat()),
            PieEntry(failedUnitsNumber.toFloat()),
            PieEntry(remainedUnitsNumber.toFloat())
        )
        val pieDataSet = PieDataSet(pieEntries, "")
        val colors = arrayListOf(
            ContextCompat.getColor(requireContext(), R.color.cyan),
            ContextCompat.getColor(requireContext(), R.color.orange),
            ContextCompat.getColor(requireContext(), R.color.light_gray)
        )
        pieDataSet.colors = colors
        pieChart.data = PieData(pieDataSet)
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(false)
        pieChart.legend.isEnabled = false
        pieDataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return ""
            }
        }
        pieChart.animateY(1000)
        pieChart.invalidate()

        //click on buttons
        binding.btnGotoPassedLessons.setOnClickListener {
            val intent = Intent(requireContext(), Activity2::class.java)
            intent.putExtra("title", getString(R.string.passed_lessons))
            intent.putExtra("isPassed", true)
            intent.putExtra("isManual", false)
            startActivity(intent)
        }
        binding.btnGotoFailedLessons.setOnClickListener {
            val intent = Intent(requireContext(), Activity2::class.java)
            intent.putExtra("title", getString(R.string.failed_lessons))
            intent.putExtra("isPassed", false)
            intent.putExtra("isManual", false)
            startActivity(intent)
        }

        //change user info
        binding.btnChangeInfo.setOnClickListener {
            //set user info to bundle and create dialog
            val dialogEditInfo = EditDialog(this)
            val bundle = Bundle()
            bundle.putInt("current_semester", sharedPref.getInt(CURRENT_SEMESTER, 1))
            bundle.putFloat("average", sharedPref.getFloat(AVERAGE, 17f))
            bundle.putBoolean("has_failed", sharedPref.getBoolean(HAS_FAILED, false))
            bundle.putBoolean("is_Senior", sharedPref.getBoolean(IS_SENIOR, false))
            dialogEditInfo.arguments = bundle
            dialogEditInfo.show(parentFragmentManager, "")

        }
    }

    private fun countPassedUnits(): Int {
        return lessonsDao.getPassedLessons().size
    }

    private fun countFailedUnits(): Int {
        return lessonsDao.getFailedLessons().size
    }

    private fun getCurrentSemester(): String {
        return when(sharedPref.getInt(CURRENT_SEMESTER, 1)) {
            1 -> "یک"
            2 -> "دو"
            3 -> "سه"
            4 -> "چهار"
            5 -> "پنج"
            6 -> "شش"
            7 -> "هفت"
            8 -> "هشت"
            9 -> "نه"
            10 -> "ده"
            11 -> "یازده"
            12 -> "دوازده"
            else -> ""
        }
    }

    private fun getUserState(): String {
        return userStateDao.getStateName(sharedPref.getInt(USER_STATE, 2))
    }

    override fun sendUserInfo(
        currentSemester: Int,
        average: Float,
        hasFailed: Boolean,
        isSenior: Boolean
    ) {
        Toast.makeText(context, "sendUserInfo has worked!", Toast.LENGTH_SHORT).show()
    }

}