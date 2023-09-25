package com.example.courseselectionguide.classes

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.data.tables.Lessons

class UtilityClass {

    companion object {
        //function for showing data on recycler
        fun showRecyclerData(
            recycler: RecyclerView,
            list: ArrayList<Lessons>,
            context: Context,
            itemEvents: AdapterLessons.ItemEvents,
            menuResId: Int
        ) {
            val adapter = AdapterLessons(context, list, itemEvents, menuResId)
            recycler.adapter = adapter
            recycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

}