package com.example.redbluefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.setFragmentResultListener(RANDOM_NUMBER_GENERATED, this) {
                requestKey, bundle ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BlueFragment.newInstance(), "BLUE")
                        .addToBackStack("BLUE")
                        .commit()
        }
    }
}







