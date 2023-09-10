package com.example.courseselectionguide.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.data.Lessons
import com.example.courseselectionguide.databinding.CardLessonBinding

class AdapterLessons(private val itemList: ArrayList<Lessons>, private val itemEvents: ItemEvents) : RecyclerView.Adapter<AdapterLessons.ViewHolderLessons>() {
    inner class ViewHolderLessons(private val binding: CardLessonBinding) : RecyclerView.ViewHolder(binding.root) {

        //onBindViewHolder's job
        fun onBindItemList(position: Int) {
            //lesson's name
            binding.txtLessonName.text = itemList[position].lessonName

            //on click:
            binding.root.setOnClickListener {
                itemEvents.onItemClicked(itemList[position])
            }

            binding.iconOptionLesson.setOnClickListener {
                itemEvents.onOptionsIconClicked()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLessons {
        val binding = CardLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderLessons(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolderLessons, position: Int) {
        holder.onBindItemList(position)
    }

    interface ItemEvents {
        fun onItemClicked(lesson: Lessons)
        fun onOptionsIconClicked()
    }
}