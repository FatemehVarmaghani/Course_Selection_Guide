package com.example.courseselectionguide.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.databinding.ActivityMainBinding
import com.example.courseselectionguide.fragments.RecommendationFragment
import com.example.courseselectionguide.fragments.SelectedLessonsFragment
import com.example.courseselectionguide.fragments.StateFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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