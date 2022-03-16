package com.example.treespotter

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.GeoPoint
import java.util.*


private const val TAG = "TREE_MAP_FRAGMENT"

class TreeMapFragment : Fragment() {
    private  val REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 0

    private var locationPermissionGranted = false
    private var movedMapToUserLocation = false  // move map to user's location when map first loads.

    private var map: GoogleMap? = null
    private var treeList = listOf<Tree>()

    private lateinit var addTreeButton: FloatingActionButton

    private val treeMarkers = mutableListOf<Marker>()

    private val treeViewModel: TreeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TreeViewModel::class.java)
    }

    private var fusedLocationProvider: FusedLocationProviderClient? = null

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        Log.d(TAG, "Map ready")
        googleMap.setOnInfoWindowClickListener { marker ->
            val treeForMarker = marker.tag as Tree
            requestDeleteTree(treeForMarker)
        }
        map = googleMap
        updateMap()
    }


    private fun requestDeleteTree(tree: Tree) {
        AlertDialog.Builder(requireActivity())
            .setTitle(R.string.delete)
            .setMessage(getString(R.string.confirm_delete_tree, tree.name))
            .setPositiveButton(android.R.string.ok) { dialog, id ->
                treeViewModel.deleteTree(tree)
            }
            .setNegativeButton(android.R.string.cancel) { dialog, id ->
                // user cancels, no action taken
            }
            .create()
            .show()
    }


    private fun updateMap(){   // Show user's location, if location enabled, and draw tree markers

        // Was location permission granted?

        if (locationPermissionGranted) {
            if (!movedMapToUserLocation) {   // Do this when the map opens, but don't keep moving it.
                moveMapToUserLocation()
            }
            setAddTreeButtonEnabled(true)
        }

        drawTrees()
    }


    private fun setAddTreeButtonEnabled(isEnabled: Boolean) {
        addTreeButton.isClickable = isEnabled
        addTreeButton.isFocusable = isEnabled

        if (isEnabled) {
            addTreeButton.backgroundTintList = AppCompatResources.getColorStateList(requireActivity(), android.R.color.holo_green_light)
        } else {
            addTreeButton.backgroundTintList = AppCompatResources.getColorStateList(requireActivity(), android.R.color.darker_gray)
        }
    }


    private fun drawTrees() {
        if (map == null) { return }

        // If there were many markers, it would be more efficient to
        // work out what has changed, and remove old markers and add new ones.

        for (marker in treeMarkers) {
            marker.remove()
        }

        treeMarkers.clear()

        for (tree in treeList) {
            tree.latLong()?.let { latLong ->
                val markerOptions = MarkerOptions().position(latLong).title(tree.name).snippet("Spotted on ${tree.dateSpotted}")
                map?.addMarker(markerOptions)?.also { marker ->
                    treeMarkers.add(marker)
                    marker.tag = tree   // tag
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun moveMapToUserLocation() {

        if (map == null) { return }

        try {

            if (locationPermissionGranted) {
                fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())
                map?.isMyLocationEnabled = true   // show blue dot at user's location
                map?.uiSettings?.isMyLocationButtonEnabled = true  // show move to my location crosshair icon

                fusedLocationProvider?.lastLocation?.addOnCompleteListener(requireActivity()) { getLocationTask ->
                    val location = getLocationTask.result
                    if (location != null) {
                        Log.d(TAG, "User location $location")
                        val center = LatLng(location.latitude, location.longitude)

                        // Zoom level 1 = whole world, 20 = city blocks. Adjust this number if desired
                        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 10f))?.also {
                            movedMapToUserLocation = true
                        }
                    } else {
                        showSnackbar(getString(R.string.no_location))
                    }
                }

            }

        } catch (ex: SecurityException) {
            Log.e(TAG, "Showing user's location on map - permission not granted", ex)
            locationPermissionGranted = false
        }
    }


    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            Log.d(TAG, "Location permission already granted")
            updateMap()
        } else {
            Log.d(TAG, "Requesting location permission")

            val requestLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    locationPermissionGranted = true
                    setAddTreeButtonEnabled(true)
                    Log.d(TAG, "location permission granted")
                    fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())
                } else {
                    Log.d(TAG, "location permission NOT granted")
                    showSnackbar(getString(R.string.give_location_permission))
                    locationPermissionGranted = false
                }

                updateMap()  // will move to location, if permission granted, or show a "enable permission" toast.

            }

            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


    @SuppressLint("MissingPermission")  // because we should have requested permission earlier
    private fun addTreeAtLocation() {

        if (fusedLocationProvider == null)  { return }
        if (!locationPermissionGranted)  {
            showSnackbar(getString(R.string.give_location_permission))
        }

        try {
            fusedLocationProvider?.lastLocation?.addOnCompleteListener(requireActivity()) { task ->
                val location = task.result

                if (location != null) {
                    val treeName = getTreeName()
                    val tree = Tree(
                        name = treeName,
                        dateSpotted = Date(),
                        location = GeoPoint(location.latitude, location.longitude)
                    )
                    treeViewModel.addTree(tree)
                    showSnackbar(getString(R.string.tree_added, treeName))
                } else {
                    showSnackbar(getString(R.string.no_location))
                }
            }
        } catch (ex: SecurityException) {
            Log.e(TAG, "Adding tree at location - permission not granted", ex)
            // todo consider requesting permission again - but avoid nagging user.
        }
    }


    private fun getTreeName(): String {
        // Return a random tree name
        // TODO ask user for info about tree
        return listOf("Fir", "Pine", "Cedar", "Spruce", "Redwood", "Bristlecone", "Giant Sequoia", "Juniper").random()
    }


    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val mainView = inflater.inflate(R.layout.fragment_map, container, false)

        addTreeButton = mainView.findViewById<FloatingActionButton>(R.id.add_tree)
        addTreeButton.setOnClickListener {
            addTreeAtLocation()
        }

        setAddTreeButtonEnabled(false)   // will be invisible until location is available

        return mainView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        // Draw trees regardless of location availability
        treeViewModel.latestTrees.observe(requireActivity()) { trees ->
            treeList = trees
            drawTrees()
        }

        // Request permission to access device location
        requestLocationPermission()

    }


    companion object {
        @JvmStatic
        fun newInstance() = TreeMapFragment()
    }
}