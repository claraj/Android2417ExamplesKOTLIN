package com.example.maps_and_location

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {

    private val colors = mapOf(
        true to BitmapDescriptorFactory.HUE_AZURE,
        false to BitmapDescriptorFactory.HUE_ORANGE)

    private var attractions = listOf<Attraction>()

    private val attractionsViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AttractionsViewModel::class.java)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        Log.d("MAPPPPPP", "READDDDY")
        val minneapolis = LatLng(44.9, 93.1)
        googleMap.addMarker(MarkerOptions().position(minneapolis).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(minneapolis))

//        drawMarkers()
        // todo show markers
    // TODO show latest location added

        attractionsViewModel.attractionsList.observe(requireActivity()) {
//            attractions = it
            drawMarkers(googleMap, attractions)
        }

    }

    fun drawMarkers(map: GoogleMap, attractions: List<Attraction>) {
        for (attr in attractions) {
            map.addMarker(MarkerOptions()
                .position(attr.location)
                .title(attr.name)
                .icon(BitmapDescriptorFactory.defaultMarker(colors[attr.visited]!!)))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }

    companion object {
        @JvmStatic
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}