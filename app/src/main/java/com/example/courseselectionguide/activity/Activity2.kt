package com.example.courseselectionguide.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.courseselectionguide.R
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.data.SelectedLessons
import com.example.courseselectionguide.databinding.Activity2Binding

class Activity2 : AppCompatActivity() {
    private lateinit var binding: Activity2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarManual)
        //options menu listener
        binding.toolbarManual.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.filter_lessons -> {
                    Toast.makeText(this, "filter done!", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        //data for recyclerView
        val data = arrayListOf(
            SelectedLessons(1,"ریاضی عمومی 1"),
            SelectedLessons(2, "فارسی عمومی"),
            SelectedLessons(3, "فارسی عمومی"),
            SelectedLessons(4, "فارسی عمومی"),
            SelectedLessons(5, "فارسی عمومی"),
            SelectedLessons(6, "فارسی عمومی"),
            SelectedLessons(7, "فارسی عمومی"),
            SelectedLessons(8, "فارسی عمومی"),
            SelectedLessons(9, "فارسی عمومی"),
            SelectedLessons(10, "فارسی عمومی"),
            SelectedLessons(11, "فارسی عمومی"),
            SelectedLessons(12, "فارسی عمومی"),
            SelectedLessons(13, "فارسی عمومی"),
            SelectedLessons(14, "فارسی عمومی"),
            SelectedLessons(15, "فارسی عمومی"),
            SelectedLessons(16, "فارسی عمومی")
        )
        //recyclerView for lessons
        val adapter = AdapterLessons(data)
        binding.recyclerManual.adapter = adapter
        binding.recyclerManual.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_activity2, menu)
        return true
    }
}