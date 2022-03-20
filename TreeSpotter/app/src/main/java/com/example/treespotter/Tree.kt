package com.example.treespotter

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

// Have to use default arguments and nullable types
// Since there's no guarantees that a Firebase document will have any of these fields,
// we can't require that a Tree object must contain them

data class Tree(
    val name: String? = null,
    val dateSpotted: Date? = null,
    val location: GeoPoint? = null,
    var favorite: Boolean? = null,   // names starting with is confuse Firebase.
    @get:Exclude @set:Exclude var documentReference: DocumentReference? = null  // don't need to upload this back to firebase
)
