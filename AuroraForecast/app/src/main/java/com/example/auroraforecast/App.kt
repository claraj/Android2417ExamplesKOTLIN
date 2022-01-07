package com.example.auroraforecast

import android.app.Application
import android.content.Context
import android.util.Log

private const val TAG = "AURORA APPLICATION"

class App: Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        App.context = applicationContext

        Log.d(TAG,"Configuring notification channel")
        Notifications(this).createNotificationChannel()

        Log.d(TAG,"Starting period API request background worker")
        ForecastWorkRequestManager(this).start()
    }
}