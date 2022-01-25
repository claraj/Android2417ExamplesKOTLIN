package com.example.redbluefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {

    private lateinit var containerView: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add listener to the FrameLayout - effectively the whole screen
        // since there are no other listeners
        containerView = findViewById(R.id.fragment_container)
        containerView.setOnClickListener {
            swapFragment()
        }

        // Display the red fragment when the app loads
        val redFragment = RedFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, redFragment, "RED")
            .commit()
    }

    private fun swapFragment() {
        // If the red fragment is not on the screen,
        if ( supportFragmentManager.findFragmentByTag("RED") == null ) {
            // replace the current fragment with the red fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BlueFragment(), "RED")
                .commit()
        } else {
            // Otherwise, replace the current fragment with the blue fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RedFragment(), "BLUE")
                .commit()
        }
    }

}







