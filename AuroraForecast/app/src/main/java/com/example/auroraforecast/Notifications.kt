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

          val intent = Intent(context, MainActivity::class.java).apply {
               flags = Intent.FLAG_ACTIVITY_NEW_TASK
          }

          val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

          var notification = NotificationCompat.Builder(context, CHANNEL_ID)
               .setSmallIcon(R.drawable.aurora)
               .setContentTitle("Aurora Forecast")
               .setContentText(message)
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               .setContentIntent(pendingIntent)
               .setAutoCancel(true)  // remove notification when user taps it
               .build()

          val notificationId = 1234  // remember this if need to modify notification later

          with (NotificationManagerCompat.from(context)) {
               notify(notificationId, notification)
          }

     }

     fun createNotificationChannel() {

          // Only needed for newer Android.
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