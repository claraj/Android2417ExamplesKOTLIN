package com.example.treespotter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "TREE_VIEW_MODEL"

class TreeViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val treeCollectionReference = db.collection("trees")

    val latestTrees = MutableLiveData<List<Tree>>()

    private val latestTreesListener = treeCollectionReference
            .orderBy("dateSpotted", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG,"Error getting latest trees", error)
                }
                if (snapshot != null) {
                    // Simplest way - convert the snapshot to tree objects.
                    // val trees = snapshot.toObjects(Tree::class.java)

                    // However, we want to store the tree references so we'll need to loop and
                    // convert, and add the document references
                    val trees = mutableListOf<Tree>()
                    for (treeDocument in snapshot) {
                        val tree = treeDocument.toObject(Tree::class.java)
                        tree.documentReference = treeDocument.reference
                        trees.add(tree)
                    }
                    Log.d(TAG, "Trees from firebase: $trees")
                    latestTrees.postValue(trees)
                }
            }

    fun addTree(tree: Tree) {
        treeCollectionReference.add(tree)
            .addOnSuccessListener { treeDocumentReference ->
                Log.d(TAG, "Added tree document $treeDocumentReference")
                tree.documentReference = treeDocumentReference  // not used here
            }
            .addOnFailureListener { error ->
                Log.e(TAG,"Error adding tree $tree", error)
            }
    }


    override fun onCleared() {
        super.onCleared()
        latestTreesListener.remove()
        Log.d(TAG, "Removing listener")
    }


    fun deleteTree(tree: Tree) {
        tree.documentReference?.delete()
    }
}