package com.example.hydration

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterRecord(
    @PrimaryKey
    @NonNull
    val day: String,
    @NonNull
    var glasses: Int
    )    // we get a free toString function

