package com.example.auroraforecast

import android.content.Context
import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit

private const val TAG = "WORK MANAGER"

class ForecastWorkRequestManager(val context: Context) {

    fun start() {

        // Periodic work request. Check the forecast every three hours.
        val checkForecastWork = PeriodicWorkRequestBuilder<ForecastRequestWorker>(3, TimeUnit.HOURS).build()
        WorkManager.getInstance(this.context).enqueueUniquePeriodicWork(
            "NOAA Aurora Forecast Request",  // You can set this to anything you like. Can use this name to specifically cancel or work with this repeating task
            ExistingPeriodicWorkPolicy.KEEP,
            checkForecastWork)

        // Example of starting a task one time, right away - useful for testing worker.
        // Periodic tasks wait the repeatInterval before starting, and the shortest
        // interval is 15 minutes apart, so waiting for the task to run can be slow.
        // It may be useful to start the task as a one-time task and make sure the task works
        // and then replace it with periodic work.

        /*
        val checkForecastWork = OneTimeWorkRequestBuilder<ForecastRequestWorker>()
            .build()

        WorkManager.getInstance(this.context).enqueue(
            checkForecastWork)
        */

    }

    fun stopAll() {

        // Unused in this application, but included for reference

        Log.d(TAG, "Stopping all tasks")
        WorkManager.getInstance(this.context).cancelAllWork()
    }

}