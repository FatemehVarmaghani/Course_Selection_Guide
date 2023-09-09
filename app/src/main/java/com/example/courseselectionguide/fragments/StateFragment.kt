package com.example.courseselectionguide.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.Activity2
import com.example.courseselectionguide.databinding.FragmentStateBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class StateFragment : Fragment() {
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
        //pie chart
        pieChart = binding.pieChartState
        val pieEntries = arrayListOf(
            PieEntry(25f),
            PieEntry(5f),
            PieEntry(70f)
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
            startActivity(intent)
        }
        binding.btnGotoFailedLessons.setOnClickListener {
            val intent = Intent(requireContext(), Activity2::class.java)
            startActivity(intent)
        }
        binding.btnChangeInfo.setOnClickListener {
            val editInfoDialog = EditUserInfoDialog()
            editInfoDialog.show(parentFragmentManager, null)
        }
    }

}