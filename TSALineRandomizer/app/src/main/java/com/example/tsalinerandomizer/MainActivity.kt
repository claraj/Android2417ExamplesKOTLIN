package com.example.tsalinerandomizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Create global variables for the UI components that need to be accessed in code
    private lateinit var tapHereButton: Button
    private lateinit var leftArrow: ImageButton
    private lateinit var rightArrow: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the UI components that need to be accessed in code
        tapHereButton = findViewById(R.id.tap_here_button)
        leftArrow = findViewById(R.id.left_arrow)
        rightArrow = findViewById(R.id.right_arrow)

        // Add event listeners to the Button, and two ImageButton
        tapHereButton.setOnClickListener { view: View ->
            showRandomArrow()
        }

        leftArrow.setOnClickListener { view: View ->
            reset()
        }

        rightArrow.setOnClickListener { view: View ->
            reset()
        }
    }

    private fun showRandomArrow() {
        // hide the button, make it invisible
        tapHereButton.visibility = View.INVISIBLE
        // make a list of "left" and "right", choose one item from the list at random
        val direction = listOf("left", "right").random()
        // If left is chosen, make the left arrow visible. Else, make the right arrow visible
        if (direction == "left") {
            leftArrow.visibility = View.VISIBLE
        } else {
            rightArrow.visibility = View.VISIBLE
        }
    }

    private fun reset() {
        // Make the arrows invisible, make the button visible
        leftArrow.visibility = View.INVISIBLE
        rightArrow.visibility = View.INVISIBLE
        tapHereButton.visibility = View.VISIBLE
    }
}





