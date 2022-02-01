package com.example.seekbarportraitlandscape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar

import android.widget.TextView
import android.widget.Toast

import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SwitchCompat

const val EXTRA_SQUARE_SIZE = "com.example.seekbarportraitlandscape.SQUARE_SIZE"
const val EXTRA_HARD_MODE = "com.example.seekbarportraitlandscape.HARD_MODE"


class MainActivity : AppCompatActivity() {

    private lateinit var seekbar: SeekBar
    private lateinit var seekbarLabel: TextView
    private lateinit var showSquareButton: Button
    private lateinit var easyModeSwitch: SwitchCompat


    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> handleActivityResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekbar = findViewById(R.id.seek_bar)
        seekbarLabel = findViewById(R.id.seek_bar_label)
        showSquareButton = findViewById(R.id.show_square_button)
        easyModeSwitch = findViewById(R.id.easy_mode_switch)

        val initialProgress = seekbar.progress + 1
        updateLabel(initialProgress)

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateLabel(progress + 1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        showSquareButton.setOnClickListener {
            showSquare()
        }
    }

    private fun showSquare() {
        Intent(this, SquareActivity::class.java).apply {
            putExtra(EXTRA_SQUARE_SIZE, seekbar.progress + 1)
            putExtra(EXTRA_HARD_MODE, easyModeSwitch.isChecked)
            resultLauncher.launch(this)
        }
    }


    private fun handleActivityResult(result: ActivityResult) {
        // Read ActivityResult from the SquareActivity
        when (result.resultCode) {
            RESULT_OK -> {
                val intent = result.data
                val tapped = intent?.getBooleanExtra(TAPPED_INSIDE_SQUARE, false) ?: false
                val message = if (tapped) {
                    "You tapped the square"
                } else {
                    "You missed the square"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            RESULT_CANCELED -> {
                Toast.makeText(this, "You did not try to tap the square", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun updateLabel(progress: Int) {
        seekbarLabel.text = getString(R.string.seekbar_value_message, progress)
    }
}

