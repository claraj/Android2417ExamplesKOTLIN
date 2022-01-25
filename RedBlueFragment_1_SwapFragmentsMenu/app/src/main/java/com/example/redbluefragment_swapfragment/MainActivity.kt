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

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            // The id can be used to identify which menu item was tapped,
            // and take appropriate action. This function needs to return
            // true, if the menu item should then appear selected,
            // false otherwise.
            when (menuItem.itemId) {
                R.id.show_red -> {
                    showFragment("RED")
                    true
                }
                R.id.show_blue -> {
                    showFragment("BLUE")
                    true
                }
                else -> {
                    false
                }
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
                        .replace(R.id.fragment_container, RedFragment.newInstance(), "RED")
                        .commit()
                }
                "BLUE" -> {
                    // Otherwise, replace the current fragment with the blue fragment
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BlueFragment.newInstance(), "BLUE")
                        .commit()
                }
            }
        }
    }
}
