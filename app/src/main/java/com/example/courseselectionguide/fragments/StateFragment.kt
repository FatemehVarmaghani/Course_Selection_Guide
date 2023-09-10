package com.example.courseselectionguide.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.Activity2
import com.example.courseselectionguide.activity.DataEntryActivity
import com.example.courseselectionguide.activity.MainActivity
import com.example.courseselectionguide.activity.sharedPref
import com.example.courseselectionguide.databinding.DialogEditUserInfoBinding
import com.example.courseselectionguide.databinding.FragmentStateBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class StateFragment : Fragment() {
    private lateinit var binding: FragmentStateBinding
    private lateinit var dialogEditBinding: DialogEditUserInfoBinding
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

        //show user info
        sharedPref = requireContext().getSharedPreferences("primitive_data", Context.MODE_PRIVATE)
        val currentSemester = sharedPref.getInt("current_semester", 1)
        val average = sharedPref.getFloat("average", 17f)
        val hasFailed = sharedPref.getBoolean("has_failed", false)
        val isSenior = sharedPref.getBoolean("is_senior", false)
        val userState = sharedPref.getInt("user_state", 2)
        val passedUnitsNumber = 80 //count with passed lessons
        val failedUnitsNumber = 6 //count with failed lessons
        val remainedUnitsNumber = 144 - passedUnitsNumber

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

        //textViews
        binding.txtStateRemainedUnits.text = remainedUnitsNumber.toString()
        binding.txtStateFailedUnits.text = failedUnitsNumber.toString()
        binding.txtStatePassedUnits.text = passedUnitsNumber.toString()
        binding.txtStateCurrentSemester.text = currentSemester.toString()
        binding.txtStateStudentState.text = userState.toString()

        //click on buttons
        binding.btnGotoPassedLessons.setOnClickListener {
            val intent = Intent(requireContext(), Activity2::class.java)
            startActivity(intent)
        }
        binding.btnGotoFailedLessons.setOnClickListener {
            val intent = Intent(requireContext(), Activity2::class.java)
            startActivity(intent)
        }

        //change user info
        binding.btnChangeInfo.setOnClickListener {
            dialogEditBinding = DialogEditUserInfoBinding.inflate(layoutInflater)
            val dialogEditInfo = AlertDialog.Builder(context)
            dialogEditInfo.setView(dialogEditBinding.root)
            dialogEditInfo.create().show()
        }
    }

}