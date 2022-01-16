package com.example.redbluefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {

    private lateinit var containerView: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // First version - swapping fragments
        containerView = findViewById(R.id.fragment_container)
        containerView.setOnClickListener {
            swapFragment()
        }

        val redFragment = RedFragment.newInstance()

        supportFragmentManager.setFragmentResultListener("randomNumberGenerated", this) {
            requestKey, bundle ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BlueFragment(), "BLUE")
                .addToBackStack("BLUE")
                .commit()
        }

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







