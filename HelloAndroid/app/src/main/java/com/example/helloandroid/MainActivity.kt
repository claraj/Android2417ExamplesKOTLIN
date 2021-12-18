package com.example.helloandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Log an information message
        Log.i(TAG, "The activity has started.")

        val button = findViewById<Button>(R.id.tapHereButton)
        button.setOnClickListener {
            Toast.makeText(this, "Hello Android!", Toast.LENGTH_LONG).show()
        }
    }
}


