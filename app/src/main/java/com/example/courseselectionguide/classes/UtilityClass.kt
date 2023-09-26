package com.example.courseselectionguide.classes

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.courseselectionguide.activity.Activity2
import com.example.courseselectionguide.activity.MainActivity
import com.example.courseselectionguide.activity.PRIMITIVE_DATA
import com.example.courseselectionguide.activity.USER_STATE
import com.example.courseselectionguide.activity.corequisitesDao
import com.example.courseselectionguide.activity.lessonsDao
import com.example.courseselectionguide.activity.prerequisiteHistoryDao
import com.example.courseselectionguide.activity.prerequisitesDao
import com.example.courseselectionguide.activity.sharedPref
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
            val prerequisiteRelations = prerequisitesDao.getPrerequisitesByMainLesson(lesson.lessonId!!)
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
            val corequisiteRelations = corequisitesDao.getCorequisitesByMainLesson(lesson.lessonId)
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

            //check required passed units (for project & internship)
            var preUnitIsChecked = true
            if (lesson.lessonId == 49) {
                //for project
                if (lessonsDao.getPassedLessons().size < 100) {
                    preUnitIsChecked = false
                    Toast.makeText(context, "صد واحد پاس نشده!", Toast.LENGTH_SHORT).show()
                }
            } else if (lesson.lessonId == 50) {
                //for internship
                if (lessonsDao.getPassedLessons().size < 80) {
                    preUnitIsChecked = false
                    Toast.makeText(context, "هشتاد واحد پاس نشده!", Toast.LENGTH_SHORT).show()
                }
            }

            //after checking all conditions, change it to passed:
            if (preIsChecked && coIsChecked && preUnitIsChecked) {
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
            val prerequisiteRelations = prerequisitesDao.getPrerequisitesByMainLesson(lesson.lessonId!!)
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
            val corequisiteRelations = corequisitesDao.getCorequisitesByMainLesson(lesson.lessonId)
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

            //check general islamic condition
            var islamicIsChecked = false
            if (lesson.lessonTypeId == 1) {
                if (lessonsDao.countSelectedIslamics() >= 1) {
                    islamicIsChecked = false
                    Toast.makeText(context, "هر ترم فقط یک درس عمومی اسلامی!", Toast.LENGTH_SHORT).show()
                } else {
                    islamicIsChecked = true
                }
            } else {
                islamicIsChecked = true
            }

            //if not fixed: orin and type conditions
            var isFixed = false
            if (lesson.isFixed) {
                isFixed = true
            } else if (lesson.lessonTypeId == 1) {
                if (lessonsDao.countPassedLessonsByOrientation(lesson.lessonOrientationId!!) >= 1) {
                    isFixed = false
                    Toast.makeText(context, "درس مشابه پاس شده", Toast.LENGTH_SHORT).show()
                } else {
                    isFixed = true
                }
            } else if (lesson.lessonTypeId == 7) {
                if (lessonsDao.countPassedOrSelectedPracticalLessonsByType(lesson.lessonTypeId) >= 2) {
                    isFixed = false
                    Toast.makeText(context, "بیشتر از دو واحد اختیاری عملی لازم نیست", Toast.LENGTH_SHORT).show()
                } else {
                    isFixed = true
                }
            } else {
                isFixed = true
            }

            //check required passed units (for project & internship)
            var preUnitIsChecked = true
            if (lesson.lessonId == 49) {
                //for project
                if (lessonsDao.getPassedLessons().size < 100) {
                    preUnitIsChecked = false
                    Toast.makeText(context, "صد واحد پاس نشده!", Toast.LENGTH_SHORT).show()
                }
            } else if (lesson.lessonId == 50) {
                //for internship
                if (lessonsDao.getPassedLessons().size < 80) {
                    preUnitIsChecked = false
                    Toast.makeText(context, "هشتاد واحد پاس نشده!", Toast.LENGTH_SHORT).show()
                }
            }

            //checking for maximum units permitted:
            var isWithinRange = true
            val newUnitSum = lessonsDao.countUnitsSumByState(4) + lesson.unitNumber
            Log.v("newUnitSum", newUnitSum.toString())
            if (newUnitSum > getMaxUnits(context)) {
                isWithinRange = false
                Toast.makeText(context, "جمع واحدها از سقف بیشتر می شود", Toast.LENGTH_SHORT).show()
            }

            //after checking all conditions, change it to passed:
            if (preIsChecked && coIsChecked && islamicIsChecked && isFixed && preUnitIsChecked && isWithinRange) {
                lessonsDao.changeToSelected(lesson.lessonId)
                Toast.makeText(context, "اضافه شد", Toast.LENGTH_SHORT).show()
            }
        }

        fun changePassedToRemained(lesson: Lessons, context: Context) {
            lessonsDao = MainDatabase.getDatabase(context).lessonsDao
            prerequisitesDao = MainDatabase.getDatabase(context).prerequisitesDao

            //check prerequisiteness
            val prerequisites = prerequisitesDao.getPrerequisitesByPreLesson(lesson.lessonId!!)
            var checked = true
            if (prerequisites.isNotEmpty()){
                for (preRel in prerequisites) {
                    if (lessonsDao.getLessonState(preRel.mainLessonId) == 2) {
                        checked = false
                        Toast.makeText(context, "این درس پیش نیاز یک درس گذرانده است", Toast.LENGTH_SHORT).show()
                    } else if (lessonsDao.getLessonState(preRel.mainLessonId) == 4) {
                        checked = false
                        Toast.makeText(context, "این درس پیش نیاز یک درس انتخاب شده است", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            //here we go
            if (checked) {
                lessonsDao.changeToRemained(lesson.lessonId)
                Toast.makeText(context, "برداشته شد", Toast.LENGTH_SHORT).show()
            }
        }

        fun changeSelectedToRemained(lesson: Lessons, context: Context) {
            lessonsDao = MainDatabase.getDatabase(context).lessonsDao
            corequisitesDao = MainDatabase.getDatabase(context).corequisitesDao

            var checked = true
            val corequisites = corequisitesDao.getCorequisitesByCoLesson(lesson.lessonId!!)
            if (corequisites.isNotEmpty()) {
                for (coRel in corequisites) {
                    if (lessonsDao.getLessonState(coRel.mainLessonId) == 4) {
                        checked = false
                        Toast.makeText(context, "این درس هم نیاز یک درس انتخاب شده است", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (checked) {
                lessonsDao.changeToRemained(lesson.lessonId)
                Toast.makeText(context, "برداشته شد", Toast.LENGTH_SHORT).show()
            }
        }

        fun changeFailedToRemained(lesson: Lessons, context: Context) {
            lessonsDao = MainDatabase.getDatabase(context).lessonsDao
            prerequisitesDao = MainDatabase.getDatabase(context).prerequisitesDao
            corequisitesDao = MainDatabase.getDatabase(context).corequisitesDao
            prerequisiteHistoryDao = MainDatabase.getDatabase(context).prerequisiteHistoryDao

            //check prerequisite history
            val prerequisiteHistory = prerequisiteHistoryDao.getPreHistoryByPreId(lesson.lessonId!!)
            if (prerequisiteHistory.isNotEmpty()) {
                for (rel in prerequisiteHistory) {
                    prerequisitesDao.insertPreRel(rel.mainLessonId, rel.prerequisiteLessonId)
                }
                corequisitesDao.deleteByCoLesson(lesson.lessonId)
                prerequisiteHistoryDao.deleteByPreLesson(lesson.lessonId)
            }

            //change lesson state
            lessonsDao.changeToRemained(lesson.lessonId)
        }

        fun addLessonToFailed(lesson: Lessons, context: Context) {
            lessonsDao = MainDatabase.getDatabase(context).lessonsDao
            prerequisitesDao = MainDatabase.getDatabase(context).prerequisitesDao
            corequisitesDao = MainDatabase.getDatabase(context).corequisitesDao
            prerequisiteHistoryDao = MainDatabase.getDatabase(context).prerequisiteHistoryDao

            //get prerequisite relations
            val prerequisites = prerequisitesDao.getPrerequisitesByPreLesson(lesson.lessonId!!)
            if (prerequisites.isNotEmpty()) {
                //change pre to co
                for (preRel in prerequisites) {
                    corequisitesDao.insertCorRel(preRel.mainLessonId, preRel.prerequisiteLessonId)
                    prerequisiteHistoryDao.insertPreHistoryRel(preRel.mainLessonId, preRel.prerequisiteLessonId)
                }
                prerequisitesDao.deleteByPreLesson(lesson.lessonId)
                Toast.makeText(context, "done!", Toast.LENGTH_SHORT).show()
            }
            lessonsDao.changeToFailed(lesson.lessonId)
            Toast.makeText(context, "اضافه شد", Toast.LENGTH_SHORT).show()
        }

        private fun getMaxUnits(context: Context): Int {
            sharedPref = context.getSharedPreferences(PRIMITIVE_DATA, Context.MODE_PRIVATE)
            return when (sharedPref.getInt(USER_STATE, 2)) {
                1 -> 24
                2 -> 20
                3 -> 25
                4 -> 14
                else -> -1
            }
        }

    }//end of companion object

}