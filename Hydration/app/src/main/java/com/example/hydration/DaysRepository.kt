package com.example.hydration

import java.text.DateFormatSymbols
import java.util.*

class DaysRepository {

    val weekdays: List<String>
        get() {
            val dateFormatSymbols = DateFormatSymbols.getInstance(Locale.getDefault())
            // dfs.weekdays is an 8 element array, first element is blank, for example
            // { , Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday }
            // On a system that uses another language, the names of the days would be in that language.
            // so the days can be numbered starting at 1.
            // Filter out the first blank day and use a 0-based array of days.
            return dateFormatSymbols.weekdays.asList().filter { it.isNotBlank() }
        }

    val todayIndex: Int
        get() {
            // Returns 0-based index of day
            val today = Calendar.getInstance(Locale.getDefault())
            // Returns a number, not a name, 1-based so the first day of the week is 1
            val day = today.get(Calendar.DAY_OF_WEEK)
            return day - 1
        }
}