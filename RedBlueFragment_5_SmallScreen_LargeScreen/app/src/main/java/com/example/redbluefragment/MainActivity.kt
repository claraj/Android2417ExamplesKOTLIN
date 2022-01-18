package com.example.redbluefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

const val RANDOM_NUMBER_GENERATED = "com.example.redbluefragment.RANDOM_NUMBER_GENERATED"

const val CURRENT_FRAGMENT_BUNDLE_KEY = "com.example.redbluefragment.CURRENT_FRAGMENT"

enum class Fragments { RED, BLUE }

enum class WindowWidth { SMALL, LARGE }

class MainActivity : AppCompatActivity() {

    private var currentFragment = Fragments.RED

    // Assume phone, change if needed
    private var screenWidth: WindowWidth = WindowWidth.SMALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Read current fragment, if any, from saved instance state. If no instance state, assume "RED"
        val currentFragmentString = savedInstanceState?.getString(CURRENT_FRAGMENT_BUNDLE_KEY) ?: "RED"
        // Convert the string to one of the enum values
        currentFragment = Fragments.valueOf(currentFragmentString)


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
        if (currentFragment == Fragments.BLUE) {
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

            currentFragment = Fragments.BLUE
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
            currentFragment = Fragments.RED
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_FRAGMENT_BUNDLE_KEY, currentFragment.toString())
    }
}







