package com.example.courseselectionguide.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserState (
    @PrimaryKey(autoGenerate = true)
    val stateId: Int,
    val stateName: String
)