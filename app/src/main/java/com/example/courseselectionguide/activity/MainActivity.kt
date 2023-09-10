package com.example.courseselectionguide.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.data.Lessons
import com.example.courseselectionguide.databinding.ActivityMainBinding
import com.example.courseselectionguide.fragments.RecommendationFragment
import com.example.courseselectionguide.fragments.SelectedLessonsFragment
import com.example.courseselectionguide.fragments.StateFragment

lateinit var sharedPref: SharedPreferences
lateinit var dataList: ArrayList<Lessons>

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //checking entry activity
        sharedPref = this.getSharedPreferences("primitive_data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val isFirstRun = sharedPref.getBoolean("first_run", true)
        if (isFirstRun) {
            editor.putBoolean("first_run", false)
            editor.apply()
            val intent = Intent(this, DataEntryActivity::class.java)
            startActivity(intent)
        }

        //transactions for main fragments
        starter()
        binding.mainBottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.bottom_menu_State -> {
                    transactionTo(R.id.main_container_frame, StateFragment())
                    true
                }

                R.id.bottom_menu_recommendation -> {
                    transactionTo(R.id.main_container_frame, RecommendationFragment())
                    true
                }

                R.id.bottom_menu_selected -> {
                    transactionTo(R.id.main_container_frame, SelectedLessonsFragment())
                    true
                }

                else -> false

            }

        }
        binding.mainBottomNavigation.setOnItemReselectedListener { }
    }

    // to open State Fragment when the app is opened
    private fun starter() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container_frame, StateFragment())
        transaction.commit()
    }

    //to load fragments when bottom navigation icon's are active
    private fun transactionTo(containerId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.commit()
    }
}