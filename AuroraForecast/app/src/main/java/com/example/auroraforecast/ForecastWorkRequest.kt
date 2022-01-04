package com.example.auroraforecast

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class ForecastWorkRequest(val context: Context) {

    fun start() {

        // Periodic

        val checkForecastWork = PeriodicWorkRequestBuilder<ForecastRequestWorker>(3, TimeUnit.HOURS).build()
        WorkManager.getInstance(this.context).enqueueUniquePeriodicWork(
            "NOAA Aurora Forecast Request",
            ExistingPeriodicWorkPolicy.KEEP,
            checkForecastWork)


        // right away

    /*
        val checkForecastWork = OneTimeWorkRequestBuilder<ForecastRequestWorker>()
            .build()

        WorkManager.getInstance(this.context).enqueue(
            checkForecastWork)
       */

    }

    fun stop() {
        WorkManager.getInstance(this.context).cancelAllWork()
    }

}