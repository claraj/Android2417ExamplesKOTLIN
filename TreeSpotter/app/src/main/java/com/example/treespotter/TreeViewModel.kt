package com.example.treespotter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TreeViewModel: ViewModel() {

    private val TAG = "TREE_VIEW_MODEL"

    private val db = Firebase.firestore
    private val treeCollectionReference = db.collection("trees")

    val latestTrees = MutableLiveData<List<Tree>>()

    private val listener = treeCollectionReference
            .orderBy("dateSpotted", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG,"Error getting latest trees", error)
                }
                if (snapshot != null) {
                    val trees = snapshot.toObjects(Tree::class.java)
                    Log.d(TAG, "Trees updated $trees")
                    latestTrees.postValue(trees)
                }
            }

    fun addTree(tree: Tree) {
        treeCollectionReference.add(tree)
            .addOnSuccessListener { treeDocumentReference ->
                Log.d(TAG, "Added tree document $treeDocumentReference")
            }
            .addOnFailureListener { error ->
                Log.e(TAG,"Error adding tree $tree", error)
            }
    }

    override fun onCleared() {
        super.onCleared()
        listener.remove()
        Log.d(TAG, "Removing listener")

    }
}