package com.example.auroraforecast

import android.app.Application
import android.content.Context
import android.util.Log

private const val TAG = "AURORA APPLICATION"

class App: Application() {

    // The Android system instantiates an instance of this class before any activities,
    // so you can put initialization code here if needed, for example, in onCreate
    // You need to list this class in the manifest
    // This class has a reference to the app context so storing a static reference to
    // the context can be useful if other non-Activity parts of your code need a reference
    // to the app context.   The compiler will display a warning - we don't want to keep
    // static references to the app context in general since these stick around and use
    // memory, but one here is ok.

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