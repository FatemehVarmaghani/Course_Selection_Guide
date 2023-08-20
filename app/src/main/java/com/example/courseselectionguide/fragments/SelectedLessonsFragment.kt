package com.example.courseselectionguide.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.R
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.data.SelectedLessons
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
        //show lessons on RecyclerView
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
            SelectedLessons(10, "فارسی عمومی")
        )
        val lessonsAdapter = AdapterLessons(data)
        binding.recyclerSelected.adapter = lessonsAdapter
        binding.recyclerSelected.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

}