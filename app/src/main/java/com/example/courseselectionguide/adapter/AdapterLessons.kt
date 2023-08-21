package com.example.courseselectionguide.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.R
import com.example.courseselectionguide.data.SelectedLessons

class AdapterLessons(private val itemList: ArrayList<SelectedLessons>) : RecyclerView.Adapter<AdapterLessons.ViewHolderLessons>() {
    inner class ViewHolderLessons(item: View) : RecyclerView.ViewHolder(item) {
        val lessonName = item.findViewById<TextView>(R.id.txt_selected_lesson_name)
        //onBindViewHolder's job
        fun onBindItemList(position: Int) {
            lessonName.text = itemList[position].lessonName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLessons {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_lesson, parent, false)
        return ViewHolderLessons(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolderLessons, position: Int) {
        holder.onBindItemList(position)
    }
}