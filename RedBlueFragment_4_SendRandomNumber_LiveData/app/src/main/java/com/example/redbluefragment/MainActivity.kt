package com.example.redbluefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider

const val RANDOM_NUMBER_GENERATED = "com.example.redbluefragment.RANDOM_NUMBER_GENERATED"

class MainActivity : AppCompatActivity() {

    private val randomNumberViewModel: RandomNumberViewModel by lazy {
        ViewModelProvider(this).get(RandomNumberViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Display the red fragment when the app loads
        val redFragment = RedFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, redFragment, "RED")
            .commit()

        // If blue should be shown, add it "on top" of red so the back button
        // works as intended
        if (randomNumberViewModel.showBlueFragment) {
            val blueFragment = BlueFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, blueFragment, "BLUE")
                .commit()
        }

        supportFragmentManager.setFragmentResultListener(RANDOM_NUMBER_GENERATED, this) {
                requestKey, bundle ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BlueFragment.newInstance(), "BLUE")
                .addToBackStack("BLUE")
                .commit()

            randomNumberViewModel.showBlueFragment = true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        randomNumberViewModel.showBlueFragment = false
    }
}







