package com.example.hydration

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterRecord(
    @PrimaryKey
    @NonNull
    var day: String,
    var glasses: Int
)

