package com.example.travelwishlist_recyclerview

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), OnListItemClickedListener, OnDataChangedListener {

    private lateinit var placeListRecyclerView: RecyclerView
    private lateinit var newPlaceEditText: EditText

    private lateinit var placesRecyclerAdapter: PlaceRecyclerAdapter

    // What event ID a done or enter key creates, varies by device.
    // Hopefully this should cover it but let me know if this doesn't work on your device
    private val doneKeyPressIDs = listOf(
        EditorInfo.IME_ACTION_DONE,
        EditorInfo.IME_ACTION_GO,
        EditorInfo.IME_ACTION_NEXT,
        EditorInfo.IME_ACTION_SEND)

    private val placesListModel: PlacesViewModel by lazy {
        ViewModelProvider(this).get(PlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeListRecyclerView = findViewById(R.id.place_list)
        newPlaceEditText = findViewById(R.id.new_place_name)

        newPlaceEditText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId in doneKeyPressIDs) {
                addNewPlace(textView.text.toString())
            }
            false  // event has not been consumed
        }

        // Configure the RecyclerView
        val places = placesListModel.getPlaces()

        placesRecyclerAdapter = PlaceRecyclerAdapter(places, this)
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

        // Create new ItemTouchHelper, pass this activity as the listener, and attach to recycler
        ItemTouchHelper(OnListItemSwipeListener( this))
            .attachToRecyclerView(placeListRecyclerView)

    }

    private fun addNewPlace(placeName: String) {
        val name = placeName.trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter a place name", Toast.LENGTH_SHORT).show()
        } else {
            val place = Place(name)
            val positionAdded = placesListModel.addNewPlace(place)
            if (positionAdded == -1) {
                Toast.makeText(this, "You already added that place", Toast.LENGTH_SHORT).show()
            } else {
                placesRecyclerAdapter.notifyItemInserted(positionAdded)
                clearForm()
                hideKeyboard()
            }
        }
    }

    override fun onListItemClicked(place: Place) {
        showMapForPlace(place)
    }

    private fun showMapForPlace(place: Place) {
        Toast.makeText(this, getString(R.string.showing_map_message, place.name), Toast.LENGTH_LONG).show()
        val placeLocationUri = Uri.parse("geo:0,0?q=${place.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
        startActivity(mapIntent)
    }

    override fun onListItemMoved(from: Int, to: Int) {
        placesListModel.movePlace(from, to)
        placesRecyclerAdapter.notifyItemMoved(from, to)
    }

    override fun onListItemDeleted(position: Int) {

        /** Delete the place at position.
         * Display a Snackbar with an undo option, and
         * restore the place if the undo action is tapped. */
        val place = placesListModel.placeAt(position)
        placesListModel.deletePlace(position)
        placesRecyclerAdapter.notifyItemRemoved(position)

        Snackbar.make(findViewById(R.id.container), getString(R.string.place_deleted, place.name), Snackbar.LENGTH_LONG)
            .setActionTextColor(resources.getColor(R.color.red))  // Newer android version requires a theme argument so the correct color can be fetched for the theme used.
            .setAction(getString(R.string.undo_delete)) {
                placesListModel.addNewPlace(place, position)
                placesRecyclerAdapter.notifyItemInserted(position)
            }
            .setBackgroundTint(resources.getColor(R.color.black))
            .show()
    }

    private fun clearForm() {
        newPlaceEditText.text.clear()
    }

    private fun hideKeyboard() {
        // Close soft (on-screen) keyboard. https://stackoverflow.com/a/1109108
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}

