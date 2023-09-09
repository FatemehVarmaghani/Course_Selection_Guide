package com.example.courseselectionguide.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.R
import com.example.courseselectionguide.activity.Activity2
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
                    val intent = Intent(context, Activity2::class.java)
                    startActivity(intent)
                    true
                }
                R.id.clear_selected_list -> {
                    //all of the selected list will be deleted
                    true
                }
                else -> false
            }
        }

//        //show lessons on RecyclerView
//        val lessonsAdapter = AdapterLessons(data)
//        binding.recyclerSelected.adapter = lessonsAdapter
//        binding.recyclerSelected.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        //lesson info dialog
        val alertDialog = DialogFragmentLessonDetail()
        alertDialog.show(parentFragmentManager, "alert dialog")
    }
}