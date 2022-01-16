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
            .add(R.id.fragment_container, redFragment, "RED")
            .commit()

        supportFragmentManager.setFragmentResultListener(RANDOM_NUMBER_GENERATED, this) {
                requestKey, bundle ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BlueFragment(), "BLUE")
                .addToBackStack("BLUE")
                .commit()
        }
    }
}







