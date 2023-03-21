package com.example.geocodingintent

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
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

    private fun openMapAppForFirstAddress(addresses: List<Address>?) {

        val address = addresses?.firstOrNull()  // null safety operator

        // The classic way - an if statement, check if address is null
        if (address == null) {
            Toast.makeText(this, getString(R.string.no_places_found_message), Toast.LENGTH_LONG).show()
        } else {
            // Create and use intent to launch map app to show location of place
            Log.d(TAG, "First place address $address")
            val geoUriString = "geo:${address.latitude},${address.longitude}"
            Log.d(TAG, "Geo URI is $geoUriString")
            val geoUri = Uri.parse(geoUriString)
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(mapIntent)
        }


        /*

        // An alternative Kotlin-esque way, using scope functions, and ? and :?
        // The code in the first lambda runs if the address is not null, the code
        // in the second lambda, using the run function, runs if the address is null.
        // The syntax uses the null safety and Elvis operators,
        // and the let and run scope functions.
        // variable?.let {} ?: run {}

        address?.let {
            // Create and use intent to launch map app to show location of place
            Log.d(TAG, "First place address $address")
            val geoUriString = "geo:${address.latitude},${address.longitude}"
            Log.d(TAG, "Geo URI is $geoUriString")
            val geoUri = Uri.parse(geoUriString)
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(mapIntent)
        } ?: run {
            Toast.makeText(this, getString(R.string.no_places_found_message), Toast.LENGTH_LONG).show()
        }

 */
    }

    private fun showMapForPlace(placeName: String) {
        // Geocode place name to find most likely location for name
        try {

            val geocoder = Geocoder(this)

            Log.d(TAG, "The build version number is ${Build.VERSION.SDK_INT}" )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Log.d(TAG, "The Android version is at least 33, using async geocoder request")
                geocoder.getFromLocationName(placeName, 1) { addresses ->
                    openMapAppForFirstAddress(addresses)
                }

            } else {
                // blocking call - code has to stop and wait until this is done (or it fails or we get an error)
                Log.d(TAG, "The Android version is less than 33, using blocking geocoder request")
                val addresses = geocoder.getFromLocationName(placeName, 1)
                openMapAppForFirstAddress(addresses)
            }

        } catch (e: IOException) {
            // This error may be thrown when geocoding, if the device does not have an internet connection
            Log.e(TAG, "Unable to geocode $placeName", e)
            Toast.makeText(this, getString(R.string.not_online_cannot_geocode_error),
                Toast.LENGTH_LONG).show()
            return
        }
    }
}

