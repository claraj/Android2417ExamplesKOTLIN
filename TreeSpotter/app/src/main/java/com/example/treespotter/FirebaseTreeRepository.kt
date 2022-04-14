package com.example.treespotter

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "FB_TREE_REPOSITORY"

class FirebaseTreeRepository(private val db: FirebaseFirestore): TreeRepository {

    // Firebase-specific code. No references to any other parts of the project.

    private val treeCollectionReference = db.collection("trees")

    // keep a reference to the listener so can stop listening when not needed.
    private var treeCollectionListener: ListenerRegistration? = null


    override fun addTree(tree: Tree) {
        treeCollectionReference.add(tree)
            .addOnSuccessListener { treeDocumentReference ->
                Log.d(TAG, "Added tree document $treeDocumentReference")
                tree.documentReference = treeDocumentReference  // not used here
            }
            .addOnFailureListener { error ->
                Log.e(TAG,"Error adding tree $tree", error)
            }
    }


    override fun deleteTree(tree: Tree) {
        tree.documentReference?.delete()
    }


    override fun setIsFavorite(tree: Tree, favorite: Boolean) {
        tree.favorite = favorite
        tree.documentReference?.update("favorite", favorite)
    }


    override fun observeTrees(notifyObserver: (List<Tree>) -> Unit) {
        treeCollectionListener = treeCollectionReference
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
                    notifyObserver(trees)
                }
            }
    }


    override fun stopObservingTrees() {
        Log.d(TAG, "Removing listener")
        treeCollectionListener?.remove()
    }

}