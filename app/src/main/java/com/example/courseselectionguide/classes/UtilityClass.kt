package com.example.courseselectionguide.classes

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.activity.corequisitesDao
import com.example.courseselectionguide.activity.lessonsDao
import com.example.courseselectionguide.activity.prerequisitesDao
import com.example.courseselectionguide.adapter.AdapterLessons
import com.example.courseselectionguide.data.databases.MainDatabase
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

        fun addLessonToPassed(lesson: Lessons, context: Context) {
            lessonsDao = MainDatabase.getDatabase(context).lessonsDao
            prerequisitesDao = MainDatabase.getDatabase(context).prerequisitesDao
            corequisitesDao = MainDatabase.getDatabase(context).corequisitesDao

            //check prerequisite
            var preIsChecked = false
            val prerequisiteRelations = prerequisitesDao.getPrerequisites(lesson.lessonId!!)
            if (prerequisiteRelations.isNotEmpty()) {
                for (preRel in prerequisiteRelations) {
                    if (lessonsDao.getLessonState(preRel.prerequisiteLessonId) != 2) {
                        preIsChecked = false
                        Toast.makeText(context, "پیش نیاز درس پاس نشده!", Toast.LENGTH_SHORT).show()
                        break
                    }
                    preIsChecked = true
                }
            } else {
                preIsChecked = true
            }

            //check corequisites
            var coIsChecked = false
            val corequisiteRelations = corequisitesDao.getCorequisites(lesson.lessonId)
            if (corequisiteRelations.isNotEmpty()) {
                for (coRel in corequisiteRelations) {
                    if (lessonsDao.getLessonState(coRel.corequisiteLessonId) != 2) {
                        coIsChecked = false
                        Toast.makeText(context, "هم نیاز درس پاس نشده!", Toast.LENGTH_SHORT).show()
                        break
                    }
                    coIsChecked = true
                }
            } else {
                coIsChecked = true
            }

            //no need to check for isFixed and orientation

            //after checking all conditions, change it to passed:
            if (preIsChecked && coIsChecked) {
                lessonsDao.changeToPassed(lesson.lessonId)
                Toast.makeText(context, "اضافه شد", Toast.LENGTH_SHORT).show()
            }
        }

        fun addLessonToSelected(lesson: Lessons, context: Context) {
            lessonsDao = MainDatabase.getDatabase(context).lessonsDao
            prerequisitesDao = MainDatabase.getDatabase(context).prerequisitesDao
            corequisitesDao = MainDatabase.getDatabase(context).corequisitesDao

            //check prerequisite
            var preIsChecked = false
            val prerequisiteRelations = prerequisitesDao.getPrerequisites(lesson.lessonId!!)
            if (prerequisiteRelations.isNotEmpty()) {
                for (preRel in prerequisiteRelations) {
                    if (lessonsDao.getLessonState(preRel.prerequisiteLessonId) != 2) {
                        preIsChecked = false
                        Toast.makeText(context, "پیش نیاز درس پاس نشده!", Toast.LENGTH_SHORT).show()
                        break
                    }
                    preIsChecked = true
                }
            } else {
                preIsChecked = true
            }

            //check corequisites
            var coIsChecked = false
            val corequisiteRelations = corequisitesDao.getCorequisites(lesson.lessonId)
            if (corequisiteRelations.isNotEmpty()) {
                for (coRel in corequisiteRelations) {
                    if (lessonsDao.getLessonState(coRel.corequisiteLessonId) != 2 && lessonsDao.getLessonState(coRel.corequisiteLessonId) != 4) {
                        coIsChecked = false
                        Toast.makeText(context, "هم نیاز درس انتخاب یا پاس نشده!", Toast.LENGTH_SHORT).show()
                        break
                    }
                    coIsChecked = true
                }
            } else {
                coIsChecked = true
            }

            //after checking all conditions, change it to passed:
            if (preIsChecked && coIsChecked) {
                lessonsDao.changeToSelected(lesson.lessonId)
                Toast.makeText(context, "اضافه شد", Toast.LENGTH_SHORT).show()
            }
        }
    }

}