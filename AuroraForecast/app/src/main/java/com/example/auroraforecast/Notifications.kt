package com.example.auroraforecast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notifications(val context: Context) {

     private val CHANNEL_ID = "AURORA NOTIFICATIONS"

     fun showNotification(message: String) {

          // apply is a scope function
          // https://kotlinlang.org/docs/scope-functions.html#apply
          val intent = Intent(context, MainActivity::class.java).apply {
               flags = Intent.FLAG_ACTIVITY_NEW_TASK
          }

          val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

          // Create the notification using the builder pattern.
          val notification = NotificationCompat.Builder(context, CHANNEL_ID)
               .setSmallIcon(R.drawable.aurora_notification)  // monochrome image required or a white box will be used
               .setContentTitle("Aurora Forecast")
               .setContentText(message)
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .setContentIntent(pendingIntent)
               .setAutoCancel(true)  // remove notification when user taps it
               .build()

          val notificationId = 1234  // Set to a value of your choice. Will need to know this if need to modify notification later

          // Ask notification manager to send notification
          val notificationManager = NotificationManagerCompat.from(context)
          notificationManager.notify(notificationId, notification)

          // Alternative Kotlin recommended way to send notification, using with scope function
//          with (NotificationManagerCompat.from(context)) {
//               notify(notificationId, notification)
//          }

     }

     fun createNotificationChannel() {

          // Only needed for newer Android.
          // Used by app and Android system to have more control over notifications from
          // different apps, and different types of notifications from apps, for example,
          // controlling the different types of notifications a user wants to receive and not receive.
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               val name = context.getString(R.string.channel_name)
               val descriptionText = context.getString(R.string.channel_description)
               val importance = NotificationManager.IMPORTANCE_DEFAULT
               val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
               }

               val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
               notificationManager.createNotificationChannel(channel)
          }
     }
}