package com.example.courseselectionguide.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.courseselectionguide.R
import com.example.courseselectionguide.databinding.FragmentSelectedBinding

class SelectedLessonsFragment : Fragment() {

    private lateinit var binding: FragmentSelectedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //inflating menu
        binding.toolbarSelected.inflateMenu(R.menu.menu_selected_fragment)
        //menu listener
        binding.toolbarSelected.setOnMenuItemClickListener {

            when(it.itemId) {
                R.id.add_new_lesson -> {
                    //add a new lesson to selected list
                    //by opening the list of lessons which can be selected
                    //depending on your state
                    true
                }
                R.id.clear_selected_list -> {
                    //all of the selected list will be deleted
                    true
                }
                else -> false
            }

        }

    }

}