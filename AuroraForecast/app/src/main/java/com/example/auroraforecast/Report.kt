package com.example.auroraforecast

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class Report(val stringDate: String, val kp: Int, val status: String) {

    private val pattern = "yyyy-MM-dd HH:mm:ss"

    val date: Date = SimpleDateFormat(pattern).parse(stringDate)
}