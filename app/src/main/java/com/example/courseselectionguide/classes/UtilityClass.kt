package com.example.courseselectionguide.classes

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.data.data_classes.Lessons

class UtilityClass {

    companion object {
        //function for showing data on recycler
        fun showRecyclerData(
            recycler: RecyclerView,
            list: ArrayList<Lessons>,
            context: Context,
            itemEvents: AdapterLessons.ItemEvents
        ) {
            val adapter = AdapterLessons(context, list, itemEvents)
            recycler.adapter = adapter
            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

}