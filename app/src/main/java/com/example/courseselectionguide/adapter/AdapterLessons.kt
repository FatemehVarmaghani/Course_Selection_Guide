package com.example.courseselectionguide.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.R
import com.example.courseselectionguide.data.tables.Lessons
import com.example.courseselectionguide.databinding.CardLessonBinding

class AdapterLessons(private val context: Context, private val itemList: ArrayList<Lessons>, private val itemEvents: ItemEvents) : RecyclerView.Adapter<AdapterLessons.ViewHolderLessons>() {
    inner class ViewHolderLessons(private val binding: CardLessonBinding) : RecyclerView.ViewHolder(binding.root) {

        //onBindViewHolder's job
        fun onBindItemList(position: Int) {
            //lesson's name
            binding.txtLessonName.text = itemList[position].lessonName

            //click on item:
            binding.root.setOnClickListener {
                itemEvents.onItemClicked(itemList[position])
            }

            //click on item's options menu
            binding.iconOptionLesson.setOnClickListener {
                val popupMenu = PopupMenu(context, binding.root)
                popupMenu.inflate(R.menu.menu_lesson_item)
                popupMenu.show()
                itemEvents.onOptionsIconClicked(binding.root, popupMenu)
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
        fun onOptionsIconClicked(item: View, popupMenu: PopupMenu)
    }
}