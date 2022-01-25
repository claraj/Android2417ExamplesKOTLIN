package com.example.redbluefragment_swapfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var containerView: View
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        containerView = findViewById(R.id.fragment_container)

        // Display the red fragment when the app loads
        val redFragment = RedFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, redFragment, "RED")
            .commit()

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            Log.d("MainActivity", menuItem.toString())
            when (menuItem.itemId) {
                R.id.show_red -> {
                    showFragment("RED")
                    true
                }
                R.id.show_blue -> {
                    showFragment("BLUE")
                    true
                }
                else -> { false }
            }
        }
    }

    private fun showFragment(tag: String) {
        // if the fragment requested is not on the screen,
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            when (tag) {
                "RED" -> {
                    // replace the current fragment with the red fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, RedFragment(), "RED")
                        .commit()
                }
                "BLUE" -> {
                    // Otherwise, replace the current fragment with the blue fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BlueFragment(), "BLUE")
                        .commit()
                }
            }
        }
    }
}
