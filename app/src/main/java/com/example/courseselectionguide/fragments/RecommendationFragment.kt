package com.example.courseselectionguide.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.courseselectionguide.R
import com.example.courseselectionguide.databinding.FragmentRecommendationBinding

class RecommendationFragment : Fragment() {

    private lateinit var binding: FragmentRecommendationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecommendationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //inflating menu
        binding.toolbarRecommended.inflateMenu(R.menu.menu_recommended_fragment)
        //menu listener
        binding.toolbarRecommended.setOnMenuItemClickListener {

            when(it.itemId) {
                R.id.add_recommended_to_selected -> {
                    //add current recommended list to selected and clear recommended list
                    true
                }
                R.id.show_new_recommended_list -> {
                    //clear current recommended list and show a new one
                    true
                }
                else -> false
            }

        }

    }

}