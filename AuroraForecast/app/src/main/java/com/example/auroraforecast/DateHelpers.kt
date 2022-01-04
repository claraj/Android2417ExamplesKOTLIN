package com.example.auroraforecast

import java.text.SimpleDateFormat
import java.util.*

val noaaDateFormat = "yyyy-MM-dd hh:mm:ss"  // 2021-12-01 21:00:00
val presentationDateFormat = "MM/dd/yy h a"  // 01/12/21 9 PM

val noaaDateFormatter = SimpleDateFormat(noaaDateFormat, Locale.getDefault())
val presentationDateFormatter = SimpleDateFormat(presentationDateFormat, Locale.getDefault())

fun noaaStringToDate(noaaString: String): Date? {
    return noaaDateFormatter.parse(noaaString)   // returns null if date can't be parsed
}

fun dateToUserString(date: Date): String {
    return presentationDateFormatter.format(date)
}

fun noaaDateStringToUserString(noaaString: String): String {
    val date = noaaStringToDate(noaaString)
    return if (date != null) {
        dateToUserString(date)
    } else {
        "Invalid date"
    }
}