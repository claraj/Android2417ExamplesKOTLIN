package com.example.travelwishlist_recyclerview

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), OnListItemClickedListener, OnDataChangedListener {

    private lateinit var placeListRecyclerView: RecyclerView
    private lateinit var newPlaceEditText: EditText
    private lateinit var addNewPlaceButton: Button

    private lateinit var loadingPlacesProgressBar: ProgressBar

    private lateinit var placesRecyclerAdapter: PlaceRecyclerAdapter

    private val placesViewModel: PlacesViewModel by lazy {
        ViewModelProvider(this).get(PlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeListRecyclerView = findViewById(R.id.place_list)
        newPlaceEditText = findViewById(R.id.new_place_name)
        addNewPlaceButton = findViewById(R.id.add_new_place_button)
        loadingPlacesProgressBar = findViewById(R.id.loading_places)

        enableDisplay(false)

        // Configure the RecyclerView with an empty list to start
        placesRecyclerAdapter = PlaceRecyclerAdapter(listOf(), this)
        placeListRecyclerView.layoutManager = LinearLayoutManager(this)
        placeListRecyclerView.adapter = placesRecyclerAdapter

        ItemTouchHelper(OnListItemSwipeListener(this))
            .attachToRecyclerView(placeListRecyclerView)

        addNewPlaceButton.setOnClickListener {
            addNewPlace()
        }

        placesViewModel.allPlaces.observe(this) { places ->
            places?.let {
                placesRecyclerAdapter.places = places
                placesRecyclerAdapter.notifyDataSetChanged()
                enableDisplay(true)
            }
        }

        placesViewModel.userMessage.observe(this) { message ->
            if (message != null) {
                Snackbar.make(findViewById(R.id.wishlist_container), message, Snackbar.LENGTH_LONG).show()
            }
        }
    }


    private fun addNewPlace() {
        val placeName = newPlaceEditText.text.toString()
        val name = placeName.trim()
        if (name.isEmpty()) {
            Snackbar.make(findViewById(R.id.wishlist_container), getString(R.string.no_place_name_error), Snackbar.LENGTH_LONG).show()
        } else {
            val place = Place(name)
            placesViewModel.addNewPlace(place)
            clearForm()
            hideKeyboard()
        }
    }


    override fun onMapRequestButtonClicked(place: Place) {
        showMapForPlace(place)
    }


    private fun showMapForPlace(place: Place) {
        Toast.makeText(this, getString(R.string.showing_map_message, place.name), Toast.LENGTH_LONG).show()
        val placeLocationUri = Uri.parse("geo:0,0?q=${place.name}")
        val mapIntent = Intent(Intent.ACTION_VIEW, placeLocationUri)
        startActivity(mapIntent)
    }


    override fun onStarredStatusChanged(place: Place, isStarred: Boolean) {
        place.starred = isStarred
        placesViewModel.updatePlace(place)
    }


    override fun onListItemDeleted(position: Int) {
        val place = placesRecyclerAdapter.places[position]
        placesViewModel.deletePlace(place)
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

    private fun enableDisplay(canInteract: Boolean) {
        if (canInteract) {
            loadingPlacesProgressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            loadingPlacesProgressBar.visibility = View.VISIBLE
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

}

