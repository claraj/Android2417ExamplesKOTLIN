package com.example.seekbarportraitlandscape

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var seekbar: SeekBar
    private lateinit var seekbarLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekbar = findViewById(R.id.seek_bar)
        seekbarLabel = findViewById(R.id.seek_bar_label)

        val initialProgress = seekbar.progress
        updateLabel(initialProgress)

        seekbar.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateLabel(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    fun updateLabel(progress: Int) {
        seekbarLabel.text = getString(R.string.seekbar_value_message, progress)
    }
}

