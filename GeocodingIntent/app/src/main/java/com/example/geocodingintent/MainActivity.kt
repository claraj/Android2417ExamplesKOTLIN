package com.example.geocodingintent

import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.IOException

private const val TAG = "GEOCODE_PLACE_ACTIVITY"

class MainActivity : AppCompatActivity() {

    private lateinit var placeNameInput: EditText
    private lateinit var mapButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeNameInput = findViewById(R.id.place_name_input)
        mapButton = findViewById(R.id.map_button)

        mapButton.setOnClickListener {
            val placeName = placeNameInput.text.toString()
            if (placeName.isEmpty()) {
                Toast.makeText(this, "Enter a place name to map", Toast.LENGTH_LONG).show()
            } else {
                Log.d(TAG, "About to geocode $placeName")
                showMapForPlace(placeName)
            }
        }
    }

    private fun showMapForPlace(placeName: String) {
        // Geocode place name to find most likely location for name
        val geocoder = Geocoder(this)
        try {
            val addresses = geocoder.getFromLocationName(placeName, 1)
            if (addresses.isEmpty()) {
                Toast.makeText(this, "No places found with this name", Toast.LENGTH_LONG).show()
            } else {
                // Create and use intent to launch map app to show location of place
                val address = addresses[0]
                Log.d(TAG, "First place address $address")
                val geoUriString = "geo:${address.latitude},${address.longitude}"
                Log.d(TAG, "Geo URI is $geoUriString")
                val geoUri = Uri.parse(geoUriString)
                val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
                startActivity(mapIntent)
            }
        } catch (e: IOException) {
            // This error may be thrown when geocoding, if the device does not have an internet connection
            Log.e(TAG, "Unable to geocode $placeName", e)
            Toast.makeText(this, "Sorry, unable to find place. Are you online?",
                Toast.LENGTH_LONG).show()
            return;
        }
    }
}

