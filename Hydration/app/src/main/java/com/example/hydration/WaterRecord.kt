package com.example.hydration

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.lang.IllegalArgumentException
import java.sql.SQLException

@Entity
class WaterRecord(
    @PrimaryKey
    @NonNull
    val day: String,
    glasses: Int    // Not var/val so can use custom getter & setter
) {

    companion object {
        const val MAX_GLASSES = 3
    }

    var glasses: Int = glasses
        set(value) {
            if (value < 0 || value > MAX_GLASSES) {
                throw IllegalArgumentException("Glasses must be between 0 and $MAX_GLASSES")
            }
            field = value
        }


    override fun toString(): String {
        return "Day=$day, Glasses=$glasses"
    }
}


