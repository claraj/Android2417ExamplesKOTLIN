package com.example.lmgtfy

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged

class MainActivity : AppCompatActivity() {

    private lateinit var searchText: EditText
    private lateinit var searchButton: Button
    private lateinit var searchConfirmation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById(R.id.enter_search_text)
        searchButton = findViewById(R.id.search_button)
        searchConfirmation = findViewById(R.id.show_search_text)

        searchText.doAfterTextChanged {
            echoUserSearchTerm()
        }

        searchButton.setOnClickListener {
            launchSearch()
        }
    }

    private fun launchSearch() {
        val text = searchText.text
        if (text.isBlank()) {
            // show user a message to enter text
            Toast.makeText(this, getString(R.string.no_text_error_message), Toast.LENGTH_SHORT).show()
        } else {
            // show a Toast confirmation
            Toast.makeText(this, getString(R.string.searching_confirmation_message, text), Toast.LENGTH_LONG).show()
            // launch web browser to search Google.
            googleSearch(text.toString())
        }
    }

    private fun googleSearch(text: String) {
        val webAddress = "https://www.google.com/search?q=$text"
        val uri = Uri.parse(webAddress)
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(browserIntent)
    }

    private fun echoUserSearchTerm() {
        val text = searchText.text
        if (text.isNotBlank()) {
            searchConfirmation.text = getString(R.string.search_display_text, text)
        } else {
            searchConfirmation.text = getString(R.string.search_display_text, "...")
        }
    }
}

