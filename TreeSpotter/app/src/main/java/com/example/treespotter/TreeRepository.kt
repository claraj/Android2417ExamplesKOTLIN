package com.example.treespotter


// a list of things a TreeRepository needs to be able to do
// in this app, FirebaseTreeRepository implements this interface,
// but a class that connects to a database, or an API, could implement this interface,
// and all we'd need to do is swap out the initialization  in TreeApplication

interface TreeRepository {
    fun addTree(tree: Tree)
    fun deleteTree(tree: Tree)
    fun setIsFavorite(tree: Tree, favorite: Boolean)
    fun observeTrees(function: (List<Tree>) -> Unit)
    fun stopObservingTrees()
}