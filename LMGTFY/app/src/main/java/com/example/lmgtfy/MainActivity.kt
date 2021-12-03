package com.example.lmgtfy

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged


class MainActivity : AppCompatActivity() {

    private lateinit var searchText: EditText;
    private lateinit var searchButton: Button;
    private lateinit var searchConfirmation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById(R.id.enter_search_text)
        searchButton = findViewById(R.id.search_button)
        searchConfirmation = findViewById(R.id.show_search_text)

        searchText.doOnTextChanged { charSequence: CharSequence?, start: Int, before: Int, count: Int ->
            if (charSequence.isNullOrBlank()) {
                searchConfirmation.text = "..."
            } else {
                searchConfirmation.text = getString(R.string.search_display_text, charSequence)
            }
        }

        searchButton.setOnClickListener {
            val searchText = searchText.text
            if (searchText.isNullOrEmpty()) {
                // show Toast to tell user to enter some text
                Toast.makeText(this, "Enter some text to search for", Toast.LENGTH_SHORT).show()
            } else {
                // show Toast confirmation
                Toast.makeText(this, "Searching for $searchText", Toast.LENGTH_LONG).show()
                // launch web browser to search Google
                val webAddress = "https://www.google.com/search?q=$searchText"
                val uri = Uri.parse(webAddress)
                val browserIntent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(browserIntent)
            }
        }
    }
}



