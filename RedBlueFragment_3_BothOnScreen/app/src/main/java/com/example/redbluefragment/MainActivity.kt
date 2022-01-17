package com.example.redbluefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout

const val RANDOM_NUMBER_GENERATED = "com.example.redbluefragment.RANDOM_NUMBER_GENERATED"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Display the red fragment when the app loads
        val redFragment = RedFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.red_fragment_container, redFragment, "RED")
            .commit()

        // And display the blue fragment
        val blueFragment = BlueFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.blue_fragment_container, blueFragment, "BLUE")
            .commit()
    }
}







