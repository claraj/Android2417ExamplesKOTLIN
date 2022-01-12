package com.example.guestlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

const val LAST_GUEST_NAME_KEY = "last-guest-name-bundle-key"

class MainActivity : AppCompatActivity() {

//    var guestNames: MutableList<String> = mutableListOf()
    val guestListViewModel: GuestListViewModel by lazy {
        ViewModelProvider(this).get(GuestListViewModel::class.java)
    }

    lateinit var addGuestButton: Button
    lateinit var newGuestEditText: EditText
    lateinit var guestList: TextView
    lateinit var lastGuestAdded: TextView
    lateinit var clearGuestList: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addGuestButton = findViewById(R.id.add_guest_button)
        newGuestEditText = findViewById(R.id.new_guest_input)
        guestList = findViewById(R.id.list_of_guests)
        lastGuestAdded = findViewById(R.id.last_guest_added)
        clearGuestList = findViewById(R.id.clear_guests_button)

        addGuestButton.setOnClickListener {
            addNewGuest()
        }

        clearGuestList.setOnClickListener {
            guestListViewModel.clearGuestList()
            lastGuestAdded.text = ""
            updateGuestList()
        }

        val savedLastGuestMessage = savedInstanceState?.getString(LAST_GUEST_NAME_KEY)
        lastGuestAdded.text = savedLastGuestMessage

        updateGuestList()
    }

    fun addNewGuest() {
        val newGuestName = newGuestEditText.text.toString()
        if (newGuestName.isNotBlank()) {
            // guestNames.add(newGuestName)
            guestListViewModel.addGuest(newGuestName)
            updateGuestList()
            newGuestEditText.text.clear()
            lastGuestAdded.text = getString(R.string.last_guest_message, newGuestName)
        }
    }

    fun updateGuestList() {
        val guestDisplay = guestListViewModel.getSortedGuestNames().joinToString(separator="\n")
        guestList.text = guestDisplay
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_GUEST_NAME_KEY, lastGuestAdded.text.toString())
    }
}