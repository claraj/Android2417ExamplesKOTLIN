package com.example.redbluefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

const val RANDOM_NUMBER_GENERATED = "com.example.redbluefragment.RANDOM_NUMBER_GENERATED"

enum class WindowWidth { SMALL, LARGE }

class MainActivity : AppCompatActivity() {

    private val randomNumberViewModel: RandomNumberViewModel by lazy {
        ViewModelProvider(this).get(RandomNumberViewModel::class.java)
    }

    // Assume phone, change if needed
    private var screenWidth: WindowWidth = WindowWidth.SMALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val deviceSmallestWidth = resources.configuration.smallestScreenWidthDp
        Log.d("MAIN_ACTIVITY", "Device smallest width $deviceSmallestWidth")

        screenWidth = if (deviceSmallestWidth < 600) {
            WindowWidth.SMALL
        } else {
            WindowWidth.LARGE
        }

        Log.d("MAIN_ACTIVITY", "Screen width $screenWidth")

        if (screenWidth == WindowWidth.SMALL) {
            displaySwappableFragments()
        } else {
            displayBothFragments()
        }
    }

    private fun displaySwappableFragments() {
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

    private fun displayBothFragments() {

        val redFragment = RedFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.red_fragment_container, redFragment, "RED")
            .commit()

        val blueFragment = BlueFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.blue_fragment_container, blueFragment, "BLUE")
            .commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (screenWidth == WindowWidth.SMALL) {
            randomNumberViewModel.showBlueFragment = false
        }
    }
}







