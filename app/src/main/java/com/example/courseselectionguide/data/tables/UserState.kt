package com.example.courseselectionguide.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserState (
    @PrimaryKey(autoGenerate = true)
    val stateId: Int? = null,
    val stateName: String
)