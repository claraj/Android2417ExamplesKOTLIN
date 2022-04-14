package com.example.dialog_examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var showListDialogButton: Button
    private lateinit var showTextEntryDialogButton: Button
    private lateinit var listDialogSelectionText: TextView
    private lateinit var textDialogEntryText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showListDialogButton = findViewById(R.id.show_list_dialog)
        showTextEntryDialogButton = findViewById(R.id.show_text_dialog)
        listDialogSelectionText = findViewById(R.id.list_dialog_choice)
        textDialogEntryText = findViewById(R.id.text_dialog_entry)

        showListDialogButton.setOnClickListener {

            val otherDataNeededForAction = "whatever this may be"

            // call the function that shows the dialog, include callback
            getFavoriteMarineCreatureList { creature ->
                // can now use the creature string variable here
                // and whatever data is in the containing function
                Log.d("TAG", "$otherDataNeededForAction, $creature")
            }
        }

        showTextEntryDialogButton.setOnClickListener {
            getFavoriteMarineCreatureText()
        }
    }


    private fun getFavoriteMarineCreatureList( callback: (String) -> Unit ) {

        // TODO string resources and string array resources for the text displayed.

        val choices = arrayOf("Squid", "Crab", "Sea Sponge")

        // When this code is in a fragment, you'll need to use requireActivity() instead of this.

        AlertDialog.Builder(this)
            .setTitle("Select a marine creature")
            // can either have a message, OR items.
            .setItems(choices) { dialog, indexItemSelected ->
                // use indexItemSelected parameter, the index of the array of options,
                // to figure out what the user chose.
                val choice = choices[indexItemSelected]
                listDialogSelectionText.text = "You chose $choice"
                // And/or, call some function in the activity/fragment
                // and/or call a designated callback function
                callback(choice)
            }
            // Setting a positive button isn't usually done with lists since the
            // dialog closes when the user makes a choice, and the set items listener
            // is called when the user taps the list item of their choice.
            .setNegativeButton("Cancel") { dialog, id ->
                // optional - useful in this case to allow user to cancel and not make a selection.
                listDialogSelectionText.text = "You cancelled the dialog."
            }
            .create()
            .show()
    }


    private fun getFavoriteMarineCreatureText() {

        // Custom dialog built from layout you create.

        // TODO string resources and string array resources for the text displayed.

        val dialogView = layoutInflater.inflate(R.layout.text_entry_dialog, null)
        val textEntryTextView = dialogView.findViewById<TextView>(R.id.favorite_marine_creature)

        AlertDialog.Builder(this)
            .setView(dialogView)
            // You may not need to set the title, it may be preferable
            // to set all the visible content in the layout
            // .setTitle("Please choose...")
            .setPositiveButton("OK") { dialog, id ->
                val userText = textEntryTextView.text
                textDialogEntryText.text = "You entered $userText"

            }
            .setNegativeButton("Cancel") { dialog, id ->
                // optional - useful in this case to allow user to cancel and not make a selection.
                textDialogEntryText.text = "You cancelled the dialog."
            }
            .create()
            .show()
    }

}