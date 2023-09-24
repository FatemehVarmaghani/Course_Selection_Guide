package com.example.courseselectionguide.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.courseselectionguide.data.tables.UserState

@Dao
interface UserStateDao : BaseDao<UserState> {
    @Query("select stateName from UserState where stateId = :stateId")
    fun getStateName(stateId: Int): String
}