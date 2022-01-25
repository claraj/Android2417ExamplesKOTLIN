package com.example.redbluefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deviceSmallestWidth = resources.configuration.smallestScreenWidthDp

        if (deviceSmallestWidth < 600) {
            supportFragmentManager.setFragmentResultListener(RANDOM_NUMBER_GENERATED, this) {
                    requestKey, bundle ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BlueFragment.newInstance(), "BLUE")
                    .addToBackStack("BLUE")
                    .commit()
            }
        }
    }
}










