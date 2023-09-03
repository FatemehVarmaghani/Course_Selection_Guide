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

        //action bar
        val listTitle = "آزمایشی"
        binding.toolbarManual.title = listTitle
        setSupportActionBar(binding.toolbarManual)

        //options menu listener
        binding.toolbarManual.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filter_lessons -> {
                    Toast.makeText(this, "filter done!", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
        //data for recyclerView
        val data = arrayListOf(
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50),
            SelectedLessons(lessonId = 50)
        )
        //recyclerView for lessons
        val adapter = AdapterLessons(data)
        binding.recyclerManual.adapter = adapter
        binding.recyclerManual.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu_activity2, menu)
        return true
    }
}