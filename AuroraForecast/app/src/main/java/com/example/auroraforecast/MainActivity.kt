package com.example.auroraforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var forecast: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecast = findViewById(R.id.forecast)
        Notifications(this).createNotificationChannel()

        ForecastWorkRequest(this).start()

        ForecastRequest(this).requestAurora( {

            forecast.setText(it.toString())

        }, {
            Toast.makeText(this, "Unable to fetch aurora info", Toast.LENGTH_LONG).show()
        })

        val stopButton: Button = findViewById(R.id.stop)
        stopButton.setOnClickListener {
            ForecastWorkRequest(this).stop()
        }
    }
}