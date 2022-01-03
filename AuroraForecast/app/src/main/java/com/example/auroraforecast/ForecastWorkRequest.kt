package com.example.auroraforecast

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class ForecastWorkRequest(val context: Context) {

    fun start() {
        val checkForecastWork = PeriodicWorkRequestBuilder<ForecastRequestWorker>(3, TimeUnit.HOURS).build()
//        val checkForecastWork = OneTimeWorkRequestBuilder<ForecastRequestWorker>()
////            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
//            .build()

        WorkManager.getInstance(this.context).enqueueUniquePeriodicWork(
            "NOAA Aurora Forecast Request",
            ExistingPeriodicWorkPolicy.KEEP,
            checkForecastWork)

//        WorkManager.getInstance(this.context).enqueue(
//            checkForecastWork)
    }

    fun stop() {
        WorkManager.getInstance(this.context).cancelAllWork()
    }

}